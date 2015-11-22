/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('navbarController', navbarController);

navbarController.$inject = [
    '$scope',
    '$state',
    'Auth',
    'Principal',
    '$document',
    '$mdUtil',
    '$mdSidenav',
    'authorize'
];

function navbarController($scope, $state,  Auth, Principal, $document, $mdUtil, $mdSidenav, authorize) {
    var vm = this;
    var debounceFn;

    /* 드로어 */
    vm.toggleRight = buildToggler('right');
    /* 인증체크 */
    vm.isAuthenticated = Principal.isAuthenticated;
    /* 권한체크 */
    vm.isInRoleRegister = Principal.isInRole("ROLE_REGISTER");
    vm.isInRoleAdmin = Principal.isInRole("ROLE_ADMIN");
    /* 로그아웃 */
    vm.logout = logout;
    /* 계정정보 */
    vm.account = authorize;
    vm.openMenu = openMenu;

    function openMenu($mdOpenMenu, ev) {
        $mdOpenMenu(ev);
    }

    function buildToggler(navID) {
        debounceFn = $mdUtil.debounce(function () {
            $mdSidenav(navID).toggle();
        }, 0);
        return debounceFn;
    }

    function logout() {
        Auth.logout();
    }

    /*헤더트리거*/
    $document.on('scroll', function () {
        if ($document.scrollTop() <= 50) {
            $scope.$apply(function () {
                if ($state.current.name === 'main' || $state.current.name === 'login'
                    || $state.current.name === 'join') {
                    vm.trigger = true;
                } else {
                    vm.trigger = false;
                }
            });
        } else {
            $scope.$apply(function () {
                vm.trigger = false;
            });
        }
    });
    $scope.$watch("vm.trigger", function (newVal) {
        vm.trigger = newVal;
    });
    $scope.$on('$stateChangeSuccess', function () {
        if ($state.current.name === 'main' || $state.current.name === 'login'
            || $state.current.name === 'join') {
            vm.trigger = true;
        } else {
            vm.trigger = false;
        }
    });
}
