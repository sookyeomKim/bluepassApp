/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageActionController', adminMypageRegManageActionController);

adminMypageRegManageActionController.$inject = [
    '$scope', '$log', '$timeout', '$stateParams', 'Action', 'ActionByClub', 'DeleteImgByAction', 'Alert', 'lodash'
];

function adminMypageRegManageActionController($scope, $log, $timeout, $stateParams, Action, ActionByClub, DeleteImgByAction, Alert, lodash) {
    var vm = this;


    vm.mpc = true;
    /* 클럽ID */
    vm.idBelongToClub = $stateParams.id;
    /* 액션삭제 */
    vm.deleteConfirm = deleteConfirm;
    /* 이미지삭제 */
    vm.imgDeleteConfirm = imgDeleteConfirm;

    /*클래스불러오기*/
    getActionByClubGuery();

    function getActionByClubGuery() {
        return ActionByClub.query({id: vm.idBelongToClub}).$promise.then(function (response) {
            vm.actionList = response;
            vm.mpc = false;
        });
    }

    function deleteConfirm($event, id) {
        return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return Action.delete({id: id}).$promise.then(function () {
                var delIndex = lodash.findIndex(vm.actionList, {id: id});
                vm.actionList.splice(delIndex, 1);
                return Alert.alert1('삭제되었습니다.')
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
            })
        });
    }

    function imgDeleteConfirm($event, id, imageId) {
        return Alert.alert2($event, '이미지삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return DeleteImgByAction.imgDelete({
                id: id,
                imageId: imageId
            }).$promise.then(function () {
                    var findIndex = lodash.findIndex(vm.actionList, {id: id});
                    var delIndex = lodash.findIndex(vm.actionList[findIndex].images, {id: imageId});
                    vm.actionList[findIndex].images.splice(delIndex, 1);

                }).catch(function () {
                    return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
                })
        })
    }
}
