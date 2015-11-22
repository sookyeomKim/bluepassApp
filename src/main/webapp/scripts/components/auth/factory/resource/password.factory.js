/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Password', ['$resource', function ($resource) {
    return $resource('api/account/change_password', {}, {});
}]);

angular.module('bluepassApp').factory('PasswordResetInit', ['$resource', function ($resource) {
    return $resource('api/account/reset_password/init', {}, {});
}]);

angular.module('bluepassApp').factory('PasswordResetFinish', ['$resource', function ($resource) {
    return $resource('api/account/reset_password/finish', {}, {});
}]);
