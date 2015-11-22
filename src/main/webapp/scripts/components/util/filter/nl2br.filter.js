/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').filter("nl2br", [function () {
    return function (text) {
        return text ? text.replace(/\n\r?/g, '<br/>') : '';
    };
}]).filter('nl2br2', ['$sce', function ($sce) {
    return function (text) {
        return text ? $sce.trustAsHtml(text.replace(/\n/g, '<br/>')) : '';
    };
}]);
