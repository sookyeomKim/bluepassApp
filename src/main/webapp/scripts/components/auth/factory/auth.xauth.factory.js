/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('AuthServerProvider', AuthServerProvider);

AuthServerProvider.$inject = ['$http', 'localStorageService'];

function AuthServerProvider($http, localStorageService) {
    var authServerProvider = {
        login: login,
        logout: logout,
        getToken: getToken,
        hasValidToken: hasValidToken
    };
    return authServerProvider;

    function login(credentials) {
        var data = "username=" + credentials.username + "&password=" + credentials.password;
        return $http.post('api/authenticate', data, {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Accept": "application/json"
            }
        })
    }

    function logout() {
        // Stateless API : No server logout
        localStorageService.clearAll();
    }

    function getToken() {
        return localStorageService.get('token');
    }

    function hasValidToken() {
        var token = this.getToken();
        /*
         * if(token == undefined) { location.href = ""; }
         */
        return token && token.expires && token.expires > new Date().getTime();
    }
}
