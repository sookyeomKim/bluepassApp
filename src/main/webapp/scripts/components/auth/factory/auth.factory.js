/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory(
    'Auth',
    [
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
        'localStorageService',
        function Auth($rootScope, $state, $q, Principal, AuthServerProvider, Account, Register, RegisterAdd,
                      Activate, Password, PasswordResetInit, PasswordResetFinish, localStorageService) {
            return {
                login: function (credentials, callback) {
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
                },

                logout: function () {
                    AuthServerProvider.logout();
                    Principal.authenticate(null);
                    if ($state.current.name !== "register") {
                        $state.go('login', {}, {
                            reload: true
                        });
                    }
                },

                authorize: function () {
                    return Principal.identity().then(
                        function (response) {
                            var isAuthenticated = Principal.isAuthenticated();
                            if ($rootScope.toState.data.roles && $rootScope.toState.data.roles.length > 0
                                && !Principal.isInAnyRole($rootScope.toState.data.roles)) {
                                if (isAuthenticated) {

                                    // user is signed in but not
                                    // authorized for
                                    // desired state
                                    $state.go('accessdenied');
                                } else {
                                    // user is not authenticated.
                                    // stow the state
                                    // they wanted
                                    // before you
                                    // send them to the signin
                                    // state, so you can
                                    // return them
                                    // when you're
                                    // done
                                    $rootScope.returnToState = $rootScope.toState;
                                    $rootScope.returnToStateParams = $rootScope.toStateParams;

                                    // now, send them to the signin
                                    // state so they
                                    // can log in
                                    $state.go('login', {}, {
                                        reload: true
                                    });
                                }
                            }
                            return response;
                        });
                },
                createAccount: function (account, callback) {
                    var cb = callback || angular.noop;

                    return Register.save(account, function () {
                        return cb(account);
                    }, function (err) {
                        this.logout();
                        return cb(err);
                    }.bind(this)).$promise;
                },
                addAccount: function (account, callback) {
                    var cb = callback || angular.noop;

                    return RegisterAdd.add(account, function () {
                        return cb(account);
                    }, function (err) {
                        return cb(err);
                    }.bind(this)).$promise;
                },

                updateAccount: function (account, callback) {
                    var cb = callback || angular.noop;

                    return Account.save(account, function () {
                        return cb(account);
                    }, function (err) {
                        return cb(err);
                    }.bind(this)).$promise;
                },

                activateAccount: function (key, callback) {
                    var cb = callback || angular.noop;

                    return Activate.get(key, function (response) {
                        return cb(response);
                    }, function (err) {
                        return cb(err);
                    }.bind(this)).$promise;
                },

                changePassword: function (newPassword, callback) {
                    var cb = callback || angular.noop;

                    return Password.save(newPassword, function () {
                        return cb();
                    }, function (err) {
                        return cb(err);
                    }).$promise;
                },

                resetPasswordInit: function (mail, callback) {
                    var cb = callback || angular.noop;

                    return PasswordResetInit.save(mail, function () {
                        return cb();
                    }, function (err) {
                        return cb(err);
                    }).$promise;
                },

                resetPasswordFinish: function (key, newPassword, callback) {
                    var cb = callback || angular.noop;

                    return PasswordResetFinish.save(key, newPassword, function () {
                        return cb();
                    }, function (err) {
                        return cb(err);
                    }).$promise;
                }
            };
        }]);
