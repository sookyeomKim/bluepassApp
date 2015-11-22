/**
 * 만든이 : 수겨미
 */
'use strict';

var RandomValue = function () {
};

RandomValue.prototype.randomCount = function (number) {
    return Math.floor((Math.random() * number) + 1);
};

angular.module('bluepassApp').service('RandomValue', RandomValue);
