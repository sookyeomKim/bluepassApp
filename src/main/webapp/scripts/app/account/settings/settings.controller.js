/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('settingsController', ['$scope', 'Principal', function ($scope, Principal) {
    /* 로그인 정보 불러오긔 */
    Principal.identity().then(function (account) {
        $scope.settingsAccount = account;
        /*console.log(account)*/
    });
}]);
