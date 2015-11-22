/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory(
    'legendInit',
    [
        'TodayDate',
        function (TodayDate) {
            var legendinit = function (count) {
                var year = TodayDate.t_year();
                var month = TodayDate.t_month();
                var date = TodayDate.t_date() - 1;
                var day_increase = TodayDate.t_day() - 1;
                var day;
                var dayarry = [];
                for (var i = 0; i < count; i++) {
                    date = date + 1;
                    if ((month === 4 || month === 6 || month === 9 || month === 11) && date === 31) {
                        date = 1;
                        month = month + 1;
                    } else if ((month === 1 || month === 3 || month === 4 || month === 5 || month === 7
                        || month === 8 || month === 10 || month === 12)
                        && date === 32) {
                        date = 1;
                        month = month + 1;
                    } else if (year % 4 !== 0 && month === 2 && date === 29) {
                        date = 1;
                        month = month + 1;
                    } else if (year % 4 === 0 && month === 2 && date === 30) {
                        date = 1;
                        month = month + 1;
                    }
                    /* 피보나치수열 돼버림.. */
                    /* var day_increase= (day_increase||1)+i; */
                    day_increase = day_increase + 1;
                    if (day_increase === 7) {
                        day_increase = 0;
                    }
                    day = TodayDate.t_daylabel(day_increase);

                    var days = {};
                    days.year = year;
                    days.month = month;
                    days.date = date;
                    days.day = day;
                    dayarry.push(days);
                }
                return dayarry;
            };

            return {
                legendinit: legendinit
            }
        }]);
