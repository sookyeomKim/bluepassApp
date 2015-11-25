/**
 * 만든이 : 수겨미
 */
"use strict";

angular
    .module('bluepassApp')
    .value('THROTTLE_MILLISECONDS', 150)
    .config(appConfigure);

appConfigure.$inject = [
    '$stateProvider',
    '$urlRouterProvider',
    '$httpProvider',
    'tmhDynamicLocaleProvider',
    'httpRequestInterceptorCacheBusterProvider',
    'ezfbProvider',
    'localStorageServiceProvider'
];

function appConfigure($stateProvider, $urlRouterProvider, $httpProvider,
                      tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider, ezfbProvider, localStorageServiceProvider) {
    /*rest api 요청 캐싱제거*/
    httpRequestInterceptorCacheBusterProvider
        .setMatchlist([/.*api.*!/, /.*protected.*!/], true);

    /*rest api 요청전에 인증 권한 체크*/
    $httpProvider.interceptors.push('AuthInterceptor');

    /* 유효하지 않는 url입력시 액션 */
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('site', {
        abstract: true,
        views: {
            'navbar@': {
                templateUrl: 'scripts/app/navbar/navbar.html',
                controller: 'navbarController',
                controllerAs: 'vm'
            },
            'drawer@': {
                templateUrl: 'scripts/app/drawer/drawer.html',
                controller: 'drawerController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            authorize: ['Auth', function (Auth) {
                return Auth.authorize();
            }]
        }
    });
    $stateProvider
        .state(
        'marketing',
        {
            parent: 'site',
            url: '/a',
            data: {
                roles: ['ROLE_USER']
            },
            resolve: {
                redirect: [
                    '$window',
                    function ($window) {
                        $window.location.href = "http://www.bluepass.co/#/?utm_source=flyer2&utm_medium=10000coupon&utm_campaign=offline&utm_content=exerise";
                    }]
            }
        });

    ezfbProvider.setLocale('ko_KR');
    ezfbProvider.setInitParams({
        appId: '401067576571727',
        version: 'v2.4'
    });

    /*쿠키 key 이름 설정*/
    localStorageServiceProvider.setPrefix('bluepassApp').setNotify(true, true);

    /*지역화 파일 불러오기*/
    tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
    tmhDynamicLocaleProvider.useCookieStorage();
}

