/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module(
    'bluepassApp',
    ['LocalStorageModule',
        'tmh.dynamicLocale',
        'ngResource',
        'ui.router',
        'ngCookies',
        'ngSanitize',
        'ngCacheBuster',
        'infinite-scroll',
        'ngMaterial',
        'ngMessages',
        'duScroll',
        'angularFileUpload',
        'mgo-angular-wizard',
        'ui.router.title',
        'ngLodash',
        'timer',
        'ezfb',
        'datatables', 'datatables.columnfilter',
        'youtube-embed',
        'angularLoad',
        'ui.bootstrap',
        'angular.filter',
        'vAccordion'
    ]).run(appRun);

appRun.$inject = [
    '$rootScope',
    '$location',
    '$window',
    '$state',
    'Auth',
    'Principal',
    '$mdBottomSheet',
    'AuthServerProvider',
    'DTDefaultOptions'
];

function appRun($rootScope, $location, $window, $state, Auth, Principal,
                $mdBottomSheet, AuthServerProvider, DTDefaultOptions) {
    /* 데이터테이블 세팅 */
    DTDefaultOptions.setLanguage({
        "sEmptyTable": "데이터가 없습니다",
        "sInfo": "_START_ - _END_ / _TOTAL_",
        "sInfoEmpty": "0 - 0 / 0",
        "sInfoFiltered": "(총 _MAX_ 개)",
        "sInfoPostFix": "",
        "sInfoThousands": ",",
        "sLengthMenu": "페이지당 줄수 _MENU_",
        "sLoadingRecords": "읽는중...",
        "sProcessing": "처리중...",
        "sSearch": "검색:",
        "sZeroRecords": "검색 결과가 없습니다",
        "oPaginate": {
            "sFirst": "처음",
            "sLast": "마지막",
            "sNext": "다음",
            "sPrevious": "이전"
        },
        "oAria": {
            "sSortAscending": ": 오름차순 정렬",
            "sSortDescending": ": 내림차순 정렬"
        }
    });

    /* 픽셀코드 */
    $window._fbq.push(['track', 'PixelInitialized', {}]);


    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
        /*
         * $stateChangeSuccess보다 라이프사이클이 빠르므로 현재 페이지의 상태에 의존성을
         * 갖는 액션을 취할 때 $stateChangeStart의 tostate를 이용
         */
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;

        /* 페이지이동마다 권한 유효성 검사 */
        if (Principal.isIdentityResolved()) {
            Auth.authorize();
        }

        /* 토큰이 사라졌을 경우 */
        if (Principal.isAuthenticated()) {
            if (!AuthServerProvider.hasValidToken()) {
                $state.go("login");
            }
        }

        /*푸터 닫기*/
        $mdBottomSheet.cancel();
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.previousStateName = fromState.name;
        $rootScope.previousStateParams = fromParams;
        /* 구글 애널리틱스 */
        if ($window.ga) {
            $window.ga('send', 'pageview', {
                page: $location.path()
            });
        }
    });

    /* 로그인액션 */
    $rootScope.back = function () {
        $state.go('actionListImgtype', {}, {
            reload: true
        });
        /*if ($rootScope.previousStateName === 'activate'
         || $state.get($rootScope.previousStateName) === null) {
         $state.go('actionListImgtype', {}, {
         reload: true
         });
         } else {
         if ($rootScope.previousStateName === "finishReset"
         || $rootScope.previousStateName === "requestReset") {
         $state.go("actionListImgtype", {}, {
         reload: true
         });
         } else {
         $state.go($rootScope.previousStateName, $rootScope.previousStateParams, {
         reload: true
         });
         }
         }*/
    };
}

angular.module('bluepassApp').controller('globalController', globalController);

globalController.$inject = [
    '$document',
    '$mdBottomSheet',
    'tmhDynamicLocale'
];

function globalController($document, $mdBottomSheet, tmhDynamicLocale) {
    var vm = this;

    /* 팝업창 */

    /*if (!localStorageService.cookie.get("popup")) {
     if
     (jQuery("#noticePopup")) {
     jQuery("#noticePopup").modal("show");
     }
     localStorageService.cookie.set("popup", "hidden", (((1 /
     24))))
     }*/

    /* 위로위로 */
    vm.toTheTop = function () {
        $document.scrollTopAnimated(0, 250);
    };

    /* 푸터 */
    vm.showListBottomSheet = function ($event) {
        $mdBottomSheet.show({
            templateUrl: 'scripts/app/footer/footer.html',
            controller: 'footerController',
            controllerAs: 'vm',
            targetEvent: $event
        })
    };

    /*지역화 세팅*/
    tmhDynamicLocale.set("ko-kr")
}
