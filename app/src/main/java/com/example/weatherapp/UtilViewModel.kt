package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import timber.log.Timber

/**
 * Created by Karli Niemann 03/31/2022
 *
 * UtilViewModel creates helper functions to easily manage user actions
 * To use, extend this ViewModel and create a sealed class containing actions in the form of
 *  sealed class ScreenViewAction {
object LoadData : ScreenViewAction()
data class ClickButton(val formData: List<String>?) : ScreenViewAction()
data class UserInput(val firstName: String, val lastName: Int) : ScreenViewAction()
}
 * Then, when using this viewmodel, [startViewModel] must be instantiated with the initial action defined (if any) in
 * the Composable being called.  [isInitialized] can be used to determine if the viewModel has been initialized yet
 *
 * From there, you may use [postAction] to post an action that will be collected in the [reduce] function
 * in the viewModel where a function can be delegated based on the action posted using a when statement. It should look like this:
 *  " postAction(ScreenViewAction.LoadData) " or "viewModel.postAction(ScreenViewAction.LoadData)" if calling from
 *  the composable
 *
 * More can be added to this to make our lives easier so don't be afraid to suggest improvements!
 */
abstract class UtilViewModel<TAction : Any, TState : Any> : ViewModel() {

    private val actionFlow = MutableSharedFlow<TAction>(extraBufferCapacity = 10)

    // Can only be collected in composable view after startViewModel() is called
    lateinit var state: MutableStateFlow<TState>

    // Must be initialized in viewModel
    abstract val initialState: TState

    var isInitialized: Boolean = false

    /*
    * Submit an action, which will be given to [reduce].
    */
    fun postAction(action: TAction) {
        Timber.v("${javaClass.simpleName}: postAction ${action.javaClass.simpleName}")
        actionFlow.tryEmit(action)
    }

    /*
    * Set the state without having to write _state.value = state.value.copy(example = example)
    * Usage: setState { state.value.copy(example = example) }
    *
    */
    fun setState(stateFlow: (TState) -> TState): TState {
        state.value = stateFlow(state.value)
        return state.value
    }

    /*
    * To use when emitting actions in a sequence
     */
    fun postActions(actions: () -> List<TAction>) {
        actions().map {
            actionFlow.tryEmit(it)
        }
    }

    /*
    * The reducer which collects the emitted actions
    */
    abstract suspend fun FlowCollector<TAction>.reduce(action: TAction)

    /*
     * This processes the action by creating a flow of the action within [reduce]
     */
    private fun processAction(action: TAction): Flow<TAction> {
        return flow {
            reduce(action)
        }
    }

    /*
     * If initial action is not null, it will emit the first action when ready
     */
    private fun startInitialAction(initialAction: TAction) {
        viewModelScope.launch {
            while (actionFlow.subscriptionCount.value == 0) {
                yield()
            }
            postAction(initialAction)
        }
    }

    /*
     * Creates action flow so every postAction() will emit an action and be collected by [reduce]
     */
    private fun constructActionLoop() {
        actionFlow.onEach { action ->
            processAction(action)
                .launchIn(scope = viewModelScope)
        }.launchIn(scope = viewModelScope)
    }

    /*
     * If using actions in viewModel, call this in composable view
     */
    open fun startViewModel(initialAction: TAction? = null) {
        state = MutableStateFlow(initialState)
        if (initialAction == null) {
            constructActionLoop()
        } else {
            startInitialAction(initialAction = initialAction)
            constructActionLoop()
        }
        isInitialized = true
    }

    //Only to be used in the instance when a viewmodel functionality is no longer needed but still remains in scope
    fun resetViewModel() {
        viewModelScope.coroutineContext.cancelChildren()
        isInitialized = false
    }
}