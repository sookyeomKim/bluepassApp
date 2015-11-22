/**
 * Created by ksk on 2015-11-15.
 */
'user strict';

angular.module('bluepassApp').controller('drawerController', drawerController);

drawerController.$inject = ['$mdSidenav', 'Auth', 'Principal', 'authorize'];

function drawerController($mdSidenav, Auth, Principal, authorize) {
    var vm = this;

    /* 인증체크 */
    vm.isAuthenticated = Principal.isAuthenticated;
    /* 권한체크 */
    vm.isInRoleRegister = Principal.isInRole("ROLE_REGISTER");
    /*드로어닫기*/
    vm.close = close;
    /* 로그아웃 */
    vm.logout = logout;

    /* 계정정보 */
    vm.account = authorize;

    function close() {
        $mdSidenav('right').close()
    }

    function logout() {
        Auth.logout();
        vm.close();
    }
}

