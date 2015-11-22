/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('AuthServerProvider',
    ['$http', 'localStorageService', function ($http, localStorageService) {
        return {
            login: function (credentials) {
                var data = "username=" + credentials.username + "&password=" + credentials.password;
                return $http.post('api/authenticate', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/json"
                    }
                })
            },
            logout: function () {
                // Stateless API : No server logout
                localStorageService.clearAll();
            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                /*
                 * if(token == undefined) { location.href = ""; }
                 */
                return token && token.expires && token.expires > new Date().getTime();
            }
        };
    }]);
