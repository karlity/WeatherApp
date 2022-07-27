package com.example.weatherapp.api

class HttpStatus {
    companion object {
        // 1×× Informational
        fun isHttpInfo(httpStatus: Int): Boolean {
            return (httpStatus / 100) == 1
        }
        const val HTTP_CONTINUE = 100
        const val HTTP_SWITCHING_PROTOCOLS = 101
        const val HTTP_PROCESSING = 102

        // 2×× Success
        fun isHttpSuccess(httpStatus: Int): Boolean {
            return (httpStatus / 100) == 2
        }
        const val HTTP_OK = 200
        const val HTTP_CREATED = 201
        const val HTTP_ACCEPTED = 202
        const val HTTP_NON_AUTHORITATIVE = 203
        const val HTTP_NO_CONTENT = 204
        const val HTTP_RESET_CONTENT = 205
        const val HTTP_PARTIAL_CONTENT = 206

        // 3×× Redirection
        fun isHttpRedirect(httpStatus: Int): Boolean {
            return (httpStatus / 100) == 3
        }
        const val HTTP_TEMPORARY_REDIRECT = 307
        const val HTTP_PERMANENT_REDIRECT = 308

        // 4×× Client Error
        fun isHttpClientError(httpStatus: Int): Boolean {
            return (httpStatus / 100) == 4
        }
        const val HTTP_BAD_REQUEST = 400
        const val HTTP_UNAUTHORIZED = 401
        const val HTTP_FORBIDDEN = 403
        const val HTTP_NOT_FOUND = 404
        const val HTTP_METHOD_NOT_ALLOWED = 405
        const val HTTP_NOT_ACCEPTABLE = 406
        const val HTTP_PROXYAUTHENTICATION_REQUIRED = 407
        const val HTTP_REQUEST_TIMEOUT = 408
        const val HTTP_CONFLICT = 409
        const val HTTP_GONE = 410
        const val HTTP_LENGTH_REQUIRED = 411
        const val HTTP_PRECONDITION_FAILED = 412
        const val HTTP_PAYLOAD_TOO_LARGE = 413
        const val HTTP_REQUEST_URI_TOO_LONG = 414
        const val HTTP_UNSUPPORTED_MEDIA_TYPE = 415
        const val HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416
        const val HTTP_EXPECTATION_FAILED = 417
        const val HTTP_IM_A_TEAPOT = 418
        const val HTTP_MISDIRECTED_REQUEST = 421
        const val HTTP_UNPROCESSABLE_ENTITY = 422
        const val HTTP_LOCKED = 423
        const val HTTP_FAILED_DEPENDENCY = 424
        const val HTTP_UPGRADE_REQUIRED = 426
        const val HTTP_PRECONDITION_REQUIRED = 428
        const val HTTP_TOO_MANY_REQUESTS = 429
        const val HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE = 431
        const val HTTP_CONNECTION_CLOSED_WITHOUT_RESPONSE = 444
        const val HTTP_UNAVAILABLE_FOR_LEGAL_REASONS = 451
        const val HTTP_HTTP_CLOSED_REQUEST = 499

        // 5×× Server Error
        fun isHttpServerError(httpStatus: Int): Boolean {
            return (httpStatus / 100) == 5
        }
        const val HTTP_INTERNAL_SERVER_ERROR = 500
        const val HTTP_NOT_IMPLEMENTED = 501
        const val HTTP_BAD_GATEWAY = 502
        const val HTTP_SERVICE_UNAVAILABLE = 503
        const val HTTP_GATEWAY_TIMEOUT = 504
        const val HTTP_VERSION_NOT_SUPPORTED = 505
        const val HTTP_VARIANTALSO_NEGOTIATES = 506
        const val HTTP_INSUFFICIENT_STORAGE = 507
        const val HTTP_LOOP_DETECTED = 508
        const val HTTP_NOT_EXTENDED = 510
        const val HTTP_NETWORK_AUTHENTICATION_REQUIRED = 511
        const val HTTP_NETWORK_CONNECT_TIMEOUT_ERROR = 599
    }
}