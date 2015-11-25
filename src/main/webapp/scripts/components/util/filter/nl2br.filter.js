/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').filter("nl2br", nl2br);

nl2br.$inject = [];

function nl2br() {
    return function (text) {
        return text ? text.replace(/\n\r?/g, '<br/>') : '';
    };
}
