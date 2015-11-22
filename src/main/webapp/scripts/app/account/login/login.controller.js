/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('loginController', loginController);

loginController.$inject = ['$rootScope', '$scope', 'Alert', '$timeout', 'Auth'];

function loginController($rootScope, $scope, Alert, $timeout, Auth) {
    var vm = this;

    vm.getLogin = getLogin;

    function getLogin(event) {
        event.preventDefault();
        Auth.login({
            username: vm.username,
            password: vm.password
        }).then(function () {
            $rootScope.back();
        }).catch(function () {
            Alert.alert1('이메일 혹은 비밀번호가 잘못 되었습니다.');
        });
    }

    $scope.$on('$stateChangeSuccess', function () {
        jQuery("body").addClass("loginBackground").addClass("imgBackgroundOn");
    });
    $scope.$on('$stateChangeStart', function () {
        jQuery("body").removeClass("loginBackground").removeClass("imgBackgroundOn");
    });

    $timeout(function () {
        angular.element('[ng-model="vm.username"]').focus();
    });
}
