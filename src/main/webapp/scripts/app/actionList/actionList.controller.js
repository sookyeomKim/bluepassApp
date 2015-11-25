/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListController', actionListController);

actionListController.$inject = [
    '$rootScope',
    '$scope',
    '$timeout',
    '$stateParams',
    'RandomValue',
    'codeNameCommonCode',
    'lodash',
    'authorize'
];

function actionListController($rootScope, $scope, $timeout, $stateParams, RandomValue, codeNameCommonCode, lodash, authorize) {
    var vm = this;

    vm.getScope = getScope;
    vm.stateConfirmRegister = lodash.findIndex(authorize.roles, function (chr) {
        return chr === 'ROLE_REGISTER'
    });
    /* breadcrumbs이미지 로테이션 */
    vm.selectImg = RandomValue.randomCount(6);
    /* 검색조건 초기화 */
    vm.queryPrams = {};
    if ($rootScope.toState.name === 'actionListImgtype') {
        vm.queryPrams.timeTypeOn = false;
        vm.queryPrams.exerciseType = "all";
        vm.queryPrams.siteType = "all";
    } else if ($rootScope.toState.name === 'actionListTabletype') {
        vm.queryPrams.timeTypeOn = true;
        vm.queryPrams.exerciseType = "all";
        vm.queryPrams.siteType = "all";
        vm.queryPrams.timeType = {
            startTime: '0600',
            endTime: '2200'
        }
    }
    /* 소개에서 종목 선택할 경우 */
    if ($stateParams.exerciseType) {
        vm.queryPrams.exerciseType = $stateParams.exerciseType
    }
    vm.exerciseTypeList = [];
    vm.timeArry = [
        {index: 0, title: '오전6시', value: '0600'},
        {index: 1, title: '오전7시', value: '0700'},
        {index: 2, title: '오전8시', value: '0800'},
        {index: 3, title: '오전9시', value: '0900'},
        {index: 4, title: '오전10시', value: '1000'},
        {index: 5, title: '오전11시', value: '1100'},
        {index: 6, title: '오후12시', value: '1200'},
        {index: 7, title: '오후1시', value: '1300'},
        {index: 8, title: '오후2시', value: '1400'},
        {index: 9, title: '오후3시', value: '1500'},
        {index: 10, title: '오후4시', value: '1600'},
        {index: 11, title: '오후5시', value: '1700'},
        {index: 12, title: '오후6시', value: '1800'},
        {index: 13, title: '오후7시', value: '1900'},
        {index: 14, title: '오후8시', value: '2000'},
        {index: 15, title: '오후9시', value: '2100'},
        {index: 16, title: '오후10시', value: '2200'}
    ];
    vm.startTimeSelected = 0;
    vm.endTimeSelected = vm.timeArry.length;

    /* 종목카테고리 초기화 */
    getExerciseType();

    function getScope() {
        return vm;
    }

    function getExerciseType() {
        return codeNameCommonCode.query({
            codeName: 'CATEGORY_SPORTART'
        }).$promise.then(function (success) {
                vm.exerciseTypeList = success;
            }).then(function () {
                $timeout(function () {
                    var owl1 = jQuery("#owlExerciseType");
                    owl1.owlCarousel({
                        itemsCustom: [[0, 3], [600, 5], [960, 7], [1200, 9]],
                        pagination: false
                    });
                    jQuery(".topMenu .next1").click(function () {
                        owl1.trigger('owl.next');
                    });
                    jQuery(".topMenu .prev1").click(function () {
                        owl1.trigger('owl.prev');
                    });
                })
            });
    }

    $scope.$watch("vm.startTimeSelected", function (newVal) {
        vm.endTimeArry = lodash.slice(vm.timeArry, newVal, vm.timeArry.length);
    });

    $scope.$watch("vm.endTimeSelected", function (newVal) {
        vm.startTimeArry = lodash.slice(vm.timeArry, 0, newVal + 1);
    });

    $scope.$watchGroup(['vm.queryPrams.exerciseType', 'vm.queryPrams.timeType.startTime', 'vm.queryPrams.timeType.endTime'], function () {
        $scope.$broadcast('QueryPrams', vm.queryPrams);
    });
}
