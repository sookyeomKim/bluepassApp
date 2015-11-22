/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('partnerRequestController', partnerRequestController);

partnerRequestController.$inject = ['lodash', 'PartnerRequest', 'Alert'];

function partnerRequestController(lodash, PartnerRequest, Alert) {
    var vm = this;

    vm.partnerRequests = [];
    vm.deleteConfirm = deleteConfirm;

    getPartnerRequest();

    function getPartnerRequest() {
        return PartnerRequest.query(function (response) {
            vm.partnerRequests = response;
        });
    }

    function deleteConfirm($event, id) {
        return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return PartnerRequest.delete({id: id}).$promise.then(function () {
                var delIndex = lodash.findIndex(vm.partnerRequests, {id: id});
                vm.partnerRequests.splice(delIndex, 1);
                return Alert.alert1('삭제되었습니다.')
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
            })
        });
    }
}
