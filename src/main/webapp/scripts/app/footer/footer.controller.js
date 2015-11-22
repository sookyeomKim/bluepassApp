/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('footerController', footerController);

footerController.$inject = ['$mdBottomSheet'];

function footerController($mdBottomSheet) {
    var vm = this;

    vm.rendering = true;
    vm.rendered = rendered;
    vm.cancleSheet = cancleSheet;

    function rendered() {
        return vm.rendering = false;
    }

    function cancleSheet() {
        return $mdBottomSheet.cancel();
    }
}
