/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .factory('Principal', ['$q', 'Account', function Principal($q, Account) {
        var _identity;
        var _authenticated = false;
        var principal = {
            isIdentityResolved: isIdentityResolved,
            isAuthenticated: isAuthenticated,
            isInRole: isInRole,
            isInAnyRole: isInAnyRole,
            authenticate: authenticate,
            identity: identity
        };

        return principal;

        function isIdentityResolved() {
            return angular.isDefined(_identity);
        }

        function isAuthenticated() {
            return _authenticated;
        }

        function isInRole(role) {
            if (!_authenticated || !_identity || !_identity.roles) {
                return false;
            }
            return _identity.roles.indexOf(role) !== -1;
        }

        function isInAnyRole(roles) {
            if (!_authenticated || !_identity.roles) {
                return false;
            }

            for (var i = 0; i < roles.length; i++) {
                if (this.isInRole(roles[i])) {
                    return true;
                }
            }

            return false;
        }

        function authenticate(identity) {
            _identity = identity;
            _authenticated = identity !== null;
        }

        function identity(force) {
            var deferred = $q.defer();

            if (force === true) {
                _identity = undefined;
            }

            if (angular.isDefined(_identity)) {
                deferred.resolve(_identity);
                return deferred.promise;
            }

            getAccount();
            function getAccount() {
                return Account.get().$promise
                    .then(getSuccess)
                    .catch(getFailed);
            }

            function getSuccess(response) {
                _identity = response.data;
                _authenticated = true;
                deferred.resolve(_identity);
            }

            function getFailed() {
                _identity = null;
                _authenticated = false;
                deferred.resolve(_identity);
            }

            return deferred.promise;
        }
    }]);
