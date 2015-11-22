/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListDetailController',
    [
        '$rootScope',
        '$scope',
        '$state',
        '$log',
        '$timeout',
        '$stateParams',
        'Action',
        'Club',
        'DataShare',
        'RandomValue',
        'Principal',
        function ($rootScope, $scope, $state, $log, $timeout, $stateParams, Action, Club, DataShare,
                  RandomValue, Principal) {
            Principal.identity().then(function (success) {
                $scope.account = success;
            });

            /* 이전버튼 */
            $scope.toPrevious = function () {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams, {
                    reload: true
                });
            };

            /* breadcrumbs이미지 로테이션 */
            $scope.selectImg = RandomValue.randomCount(6);

            /* 상세설명&강사정보 탭 */
            $scope.tabSelected = 1;

            /* 인포탭 이벤트 */
            $timeout(function () {
                jQuery("#infoTab ul li a").click(function (e) {
                    e.preventDefault();
                });
            }, 0);

            $scope.load = function (id) {
                var action = Action.get({
                    id: id
                });
                action.$promise.then(function (success) {
                    $scope.actionDetail = success;
                    $scope.classImgList = $scope.actionDetail.images;
                    DataShare.sendData($scope.actionDetail.club.id);
                }, function (error) {
                    $log.debug(error);
                }).then(function () {
                    $timeout(function () {
                        var slider = new MasterSlider();
                        slider.control('arrows');
                        /*
                         * slider.control('thumblist', { autohide :
                         * false, dir : 'h', arrows : false, align :
                         * 'bottom', width : 180, height : 170, margin :
                         * 5, space : 5 });
                         */
                        slider.setup('masterslider', {
                            width: 600,
                            height: 400,
                            space: 5,
                            view: 'fade',
                            autoplay: true
                        });
                    }, 0);
                })
            };
            $scope.load($stateParams.id);
        }]);
