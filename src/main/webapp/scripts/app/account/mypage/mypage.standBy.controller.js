/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").controller("bpStandByController", bpStandByController);

bpStandByController.$inject = ['$scope', '$stateParams', '$window', 'Principal', 'lodash', 'codeNameCommonCode'];

function bpStandByController($scope, $stateParams, $window, Principal, lodash, codeNameCommonCode) {
    var vm = this;
    vm.paymentState = Number($stateParams.paymentWay);
    vm.ticketId = null;
    vm.ticket = null;

    getAccount();
    function getAccount() {
        return Principal.identity(true).then(function (response) {
            vm.ticketId = response.ticketId;
            return getTickets(vm.ticketId);
        })
    }

    function getTickets(id) {
        return codeNameCommonCode.query({codeName: 'CATEGORY_TICKET'}).$promise.then(function (response) {
            return vm.ticket = lodash.find(response, 'id', id);
        });
    }

    $scope.$on("$stateChangeSuccess", function () {
        /* 페이스북 픽셀 코드 */
        $window._fbq.push(['track', '6025609775099', {
            'value': '0.00',
            'currency': 'KRW'
        }]);
    })
}
