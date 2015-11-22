/**
 * Created by ksk on 2015-11-13.
 */

'use strict';

angular
    .module('bluepassApp').factory('AuthInterceptor', AuthInterceptor);

AuthInterceptor.$inject = ['localStorageService'];

function AuthInterceptor(localStorageService) {
    return {
        request: function (config) {
            config.headers = config.headers || {};
            var token = localStorageService.get('token');

            if (token && token.expires && token.expires > new Date().getTime()) {
                config.headers['x-auth-token'] = token.token;
            }
            return config;
        }
    };
}
