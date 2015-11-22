/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListController',
    [
        '$rootScope',
        '$scope',
        '$filter',
        '$timeout',
        '$stateParams',
        'RandomValue',
        'codeNameCommonCode',
        'Principal',
        'Action',
        'InstructorsByAction',
        'Instructor',
        'legendInit',
        'ClassSchedule',
        'ActionByClub',
        'AddressIndex',
        'lodash',
        function ($rootScope, $scope, $filter, $timeout, $stateParams, RandomValue, codeNameCommonCode,
                  Principal, Action, InstructorsByAction, Instructor, legendInit, ClassSchedule, ActionByClub,
                  AddressIndex, lodash) {
            $scope.foucPrevent = false;

            $scope.getScope = function () {
                return $scope;
            };

            /* 예약을 위한 유저정보 불러오기 */
            Principal.identity().then(function (success) {
                $scope.account = success;
                if ($scope.account) {
                    $scope.stateConfirm1 = lodash.findIndex($scope.account.roles, function (chr) {
                        return chr === 'ROLE_REGISTER'
                    });
                }
            });

            /* breadcrumbs이미지 로테이션 */
            $scope.selectImg = RandomValue.randomCount(6);

            /* 종목카테고리 초기화 */
            codeNameCommonCode.query({
                codeName: 'CATEGORY_SPORTART'
            }).$promise.then(function (success) {
                    $scope.exerciseTypeList = success;
                }).then(function () {
                    $scope.foucPrevent = true;
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

            /* 지역카테고리 초기화 */
            /*AddressIndex.query().$promise.then(function(success) {
             $scope.siteList = success;
             }).then(function() {
             $timeout(function() {
             var owl2 = jQuery("#owlSiteType")
             owl2.owlCarousel({
             itemsCustom : [ [ 0, 3 ], [ 600, 5 ], [ 960, 7 ], [ 1200, 9 ], ],
             pagination : false
             });
             jQuery(".topMenu .next2").click(function() {
             owl2.trigger('owl.next');
             });
             jQuery(".topMenu .prev2").click(function() {
             owl2.trigger('owl.prev');
             });
             });
             })*/

            /* 검색조건 초기화 */
            if ($rootScope.toState.name === 'actionListImgtype') {
                $scope.timeTypeOn = false;
                $scope.exerciseType = "all";
                $scope.siteType = "all";
            } else if ($rootScope.toState.name === 'actionListTabletype') {
                $scope.timeTypeOn = true;
                $scope.exerciseType = "all";
                $scope.siteType = "all";
                $scope.timeType = {
                    startTime: '0600',
                    endTime: '2200'
                }
            }

            /* 시간검색메뉴 */
            $scope.getScope = function () {
                return $scope;
            };

            $scope.timeArry = [{
                index: 0,
                title: '오전6시',
                value: '0600'
            }, {
                index: 1,
                title: '오전7시',
                value: '0700'
            }, {
                index: 2,
                title: '오전8시',
                value: '0800'
            }, {
                index: 3,
                title: '오전9시',
                value: '0900'
            }, {
                index: 4,
                title: '오전10시',
                value: '1000'
            }, {
                index: 5,
                title: '오전11시',
                value: '1100'
            }, {
                index: 6,
                title: '오후12시',
                value: '1200'
            }, {
                index: 7,
                title: '오후1시',
                value: '1300'
            }, {
                index: 8,
                title: '오후2시',
                value: '1400'
            }, {
                index: 9,
                title: '오후3시',
                value: '1500'
            }, {
                index: 10,
                title: '오후4시',
                value: '1600'
            }, {
                index: 11,
                title: '오후5시',
                value: '1700'
            }, {
                index: 12,
                title: '오후6시',
                value: '1800'
            }, {
                index: 13,
                title: '오후7시',
                value: '1900'
            }, {
                index: 14,
                title: '오후8시',
                value: '2000'
            }, {
                index: 15,
                title: '오후9시',
                value: '2100'
            }, {
                index: 16,
                title: '오후10시',
                value: '2200'
            }];

            $scope.startTimeSelected = 0;

            $scope.endTimeSelected = $scope.timeArry.length;

            $scope.$watch("startTimeSelected", function (newVal) {
                $scope.endTimeArry = lodash.slice($scope.timeArry, newVal, $scope.timeArry.length);
            });

            $scope.$watch("endTimeSelected", function (newVal) {
                $scope.startTimeArry = lodash.slice($scope.timeArry, 0, newVal + 1);
            });

            /* 소개에서 종목 선택할 경우 */
            if ($stateParams.exerciseType) {
                $scope.exerciseType = $stateParams.exerciseType
            }
        }]);
