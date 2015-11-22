/**
 * 만든이 : 수겨미
 */

'use strict';

angular.module('bluepassApp').controller('activationController', activationController);

activationController.$inject = ['$scope', '$stateParams', '$window', 'Auth'];

function activationController($scope, $stateParams, $window, Auth) {
    var vm = this;

    getActivateAccount();
    function getActivateAccount() {
        return Auth.activateAccount({
            key: $stateParams.key
        }).then(function () {
            vm.error = null;
            vm.success = 'OK';
        }).catch(function () {
            vm.success = null;
            vm.error = 'ERROR';
        });
    }

    $scope.$on("$stateChangeSuccess", function () {
        /* 페이스북 픽셀 코드 */
        $window._fbq.push(['track', '6025330320899', {
            'value': '0.00',
            'currency': 'KRW'
        }]);
        $("body").addClass("activateBackGround").addClass("imgBackgroundOn");
    });

    $scope.$on('$stateChangeStart', function () {
        $("body").removeClass("activateBackGround").removeClass("imgBackgroundOn");
    });
}
