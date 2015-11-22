/**
 * 만든이 : 수겨미
 */
'use strict';

var todayLabel;
var TodayDate = function () {
    this.date = new Date();
};
TodayDate.prototype.t_year = function () {
    return this.date.getFullYear();
};
TodayDate.prototype.t_month = function () {
    return this.date.getMonth() + 1;
};
TodayDate.prototype.t_date = function () {
    return this.date.getDate();
};
TodayDate.prototype.t_day = function () {
    return this.date.getDay();
};
TodayDate.prototype.t_daylabel = function (custom_day) {
    var week = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    var day = this.date.getDay();
    if (custom_day !== undefined) {
        day = custom_day;
    }
    todayLabel = week[day];
    return todayLabel;
};
angular.module('bluepassApp').service('TodayDate', TodayDate);
