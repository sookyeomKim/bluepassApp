/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Auth', Auth);

Auth.$inject = [
    '$rootScope',
    '$state',
    '$q',
    'Principal',
    'AuthServerProvider',
    'Account',
    'Register',
    'RegisterAdd',
    'Activate',
    'Password',
    'PasswordResetInit',
    'PasswordResetFinish',
    'localStorageService'
];

function Auth($rootScope, $state, $q, Principal, AuthServerProvider, Account, Register, RegisterAdd,
              Activate, Password, PasswordResetInit, PasswordResetFinish, localStorageService) {
    var auth = {
        login: login,
        logout: logout,
        authorize: authorize,
        createAccount: createAccount,
        addAccount: addAccount,
        updateAccount: updateAccount,
        activateAccount: activateAccount,
        changePassword: changePassword,
        resetPasswordInit: resetPasswordInit,
        resetPasswordFinish: resetPasswordFinish
    };
    return auth;

    function login(credentials, callback) {
        var cb = callback || angular.noop;
        var deferred = $q.defer();

        AuthServerProvider.login(credentials).then(function (success) {
            // 로그인정보로 토큰 생성
            localStorageService.set('token', success.data);

            // 로그인 정보 갱신
            Principal.identity(true);

            deferred.resolve(success);
            return cb();// Auth.login()에 2번째 인자로 원하는 함수를 보내면
            // 해당 함수가
            // 리턴된다.
        }).catch(function (err) {
            this.logout();
            deferred.reject(err);
            return cb(err);
        }.bind(this));
        return deferred.promise;
    }

    function logout() {
        AuthServerProvider.logout();
        Principal.authenticate(null);
        if ($state.current.name !== "register") {
            $state.go('login', {}, {
                reload: true
            });
        }
    }

    function authorize() {
        return Principal.identity().then(
            function (response) {
                var isAuthenticated = Principal.isAuthenticated();
                if ($rootScope.toState.data.roles && $rootScope.toState.data.roles.length > 0
                    && !Principal.isInAnyRole($rootScope.toState.data.roles)) {
                    if (isAuthenticated) {
                        /*권한이 없을 경우*/
                        $state.go('accessdenied');
                    } else {
                        /*비로그인일 경우*/
                        $rootScope.returnToState = $rootScope.toState;
                        $rootScope.returnToStateParams = $rootScope.toStateParams;
                        $state.go('login', {}, {
                            reload: true
                        });
                    }
                }
                return response;
            });
    }

    function createAccount(account, callback) {
        var cb = callback || angular.noop;

        return Register.save(account, function () {
            return cb(account);
        }, function (err) {
            this.logout();
            return cb(err);
        }.bind(this)).$promise;
    }

    function addAccount(account, callback) {
        var cb = callback || angular.noop;

        return RegisterAdd.add(account, function () {
            return cb(account);
        }, function (err) {
            return cb(err);
        }.bind(this)).$promise;
    }

    function updateAccount(account, callback) {
        var cb = callback || angular.noop;

        return Account.save(account, function () {
            return cb(account);
        }, function (err) {
            return cb(err);
        }.bind(this)).$promise;
    }

    function activateAccount(key, callback) {
        var cb = callback || angular.noop;

        return Activate.get(key, function (response) {
            return cb(response);
        }, function (err) {
            return cb(err);
        }.bind(this)).$promise;
    }

    function changePassword(newPassword, callback) {
        var cb = callback || angular.noop;

        return Password.save(newPassword, function () {
            return cb();
        }, function (err) {
            return cb(err);
        }).$promise;
    }

    function resetPasswordInit(mail, callback) {
        var cb = callback || angular.noop;

        return PasswordResetInit.save(mail, function () {
            return cb();
        }, function (err) {
            return cb(err);
        }).$promise;
    }

    function resetPasswordFinish(key, newPassword, callback) {
        var cb = callback || angular.noop;

        return PasswordResetFinish.save(key, newPassword, function () {
            return cb();
        }, function (err) {
            return cb(err);
        }).$promise;
    }
}
