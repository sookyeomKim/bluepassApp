/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageActionEditController', adminMypageRegManageActionEditController);

adminMypageRegManageActionEditController.$inject = [
    '$scope',
    '$log',
    '$timeout',
    '$stateParams',
    '$state',
    'Action',
    'ActionByClub',
    'FileUploader',
    'codeNameCommonCode',
    'DeleteImgByAction',
    'Alert'
];

function adminMypageRegManageActionEditController($scope, $log, $timeout, $stateParams, $state, Action, ActionByClub, FileUploader,
                                                  codeNameCommonCode, DeleteImgByAction, Alert) {
    var vm = this;
    /* 클럽ID */
    vm.idBelongToClub = $stateParams.id;
    /* 초기화 */
    vm.count = 0;
    vm.photosId = [];
    vm.action = {
        id: null,
        title: null,
        description: null,
        shortDescription: null,
        useLimitType: '무제한',
        useLimitValue: null,
        category: null,
        movieIds: null,
        imageIds: [],
        clubId: vm.idBelongToClub
    };
    /* 파일업로더 */
    vm.actionUploader = new FileUploader({
        url: '/image/upload',
        queueLimit: 30,
        autoUpload: true
    });
    vm.actionUploader.onSuccessItem = onSuccessItem;
    vm.actionUploader.onErrorItem = onErrorItem;
    vm.imgUpload = imgUpload;
    vm.imgRemove = imgRemove;
    /*클래스등록*/
    vm.getAction = getAction;

    /* 클래스 불러오기 */
    if ($stateParams.actionId) {
        getActionGet($stateParams.actionId);
    }
    /*운동종목불러오기*/
    getExserciseType();

    function getActionGet(id) {
        return Action.get({id: id}).$promise.then(function (response) {
            vm.action = {
                id: response.id,
                title: response.title,
                description: response.description,
                shortDescription: response.shortDescription,
                useLimitType: response.useLimitType,
                useLimitValue: response.useLimitValue,
                category: response.category,
                movieIds: response.movieIds,
                imageIds: [],
                clubId: vm.idBelongToClub
            };
            for (var i = 0; i < response.images.length; i++) {
                vm.action.imageIds.push(response.images[i].image.id)
            }
            return
        })
    }

    function getExserciseType() {
        return codeNameCommonCode.query({codeName: 'CATEGORY_SPORTART'}).$promise.then(function (reponse) {
            vm.exserciseTypeList = reponse;
        });
    }

    function onSuccessItem(fileItem, response, status, headers) {
        vm.photoId = response.files[0].id;
        if (!vm.actionUploader.queue[vm.count].isError) {
            vm.actionUploader.queue[vm.count].index = response.files[0].id;
        }
        vm.count++;
        vm.photosId.push(response.files[0].id);
    }

    function onErrorItem(item, response, status, headers) {
        vm.count++;
    }

    function imgUpload(index) {
        vm.actionUploader.queue[index].upload();
        vm.actionUploader.queue[index].onSuccess = function (response, status, headers) {
            vm.photoId = response.files[0].id;
            vm.count++;
            vm.actionUploader.queue[index].index = response.files[0].id;
        };
    }

    function imgRemove(index) {
        if (!vm.actionUploader.queue[index].isError) {
            var idx = vm.photosId.indexOf(vm.actionUploader.queue[index].index);
            vm.photosId.splice(idx, 1);
        }
        vm.count--;
        vm.actionUploader.queue[index].remove();
    }

    function getAction() {
        vm.action.imageIds = vm.photosId;
        if (vm.action.id != null) {
            return getActionUpdate().then(getSuccess).catch(getError)
        } else {
            return getActionSave().then(getSuccess).catch(getError)
        }
    }

    function getActionSave() {
        return Action.save(vm.action).$promise
    }

    function getActionUpdate() {
        return Action.update(vm.action).$promise
    }

    function getSuccess() {
        return $state.go("adminRegManage.Action", {
            id: vm.idBelongToClub
        })
    }

    function getError() {
        return Alert.alert1('에러발생')
    }

    $timeout(function () {
        jQuery('#saveActionModal').on('hide.bs.modal', function () {
            actionUploader.clearQueue();
        });
    });
}
