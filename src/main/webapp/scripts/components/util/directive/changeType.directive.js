/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("changeType",
    ['AccountChangeType', 'lodash', function (AccountChangeType, lodash) {
        return {
            restrict: "E",
            scope: {
                changeType: "@",
                model: "@"
            },
            templateUrl: "scripts/components/util/directive/template/changeType.html",
            link: function (sco, el) {
                var changeTypeParams = {};
                var parseModel = sco.parseModel = JSON.parse(sco.model);
                var authoritiesArry = parseModel.user.authorities;
                var stateConfirm;
                var initialValue = {};
                if (sco.changeType === "VENDOR") {
                    initialValue = {
                        beforeChangeBtText: "제휴사회원전환",
                        afterChangeBtText: "제휴사회원취소",
                        type: "ROLE_VENDOR",
                        clazz: "btn-material-blue-A200"
                    }
                } else if (sco.changeType === "REGISTER") {
                    initialValue = {
                        beforeChangeBtText: "회원전환",
                        afterChangeBtText: "회원취소",
                        type: "ROLE_REGISTER",
                        clazz: "btn-material-blue-A200"
                    }
                }

                stateConfirm = lodash.findIndex(authoritiesArry, function (chr) {
                    return chr.name === 'ROLE_VENDOR';
                });
                if (stateConfirm === -1) {
                    el.find("button").removeClass(initialValue.clazz);
                    sco.buttonText = initialValue.beforeChangeBtText;
                } else {
                    el.find("button").addClass(initialValue.clazz);
                    sco.buttonText = initialValue.afterChangeBtText;
                }

                sco.changeTypeFn = function (userId) {
                    if (stateConfirm === -1) {
                        changeTypeParams = {
                            userId: userId,
                            requestType: initialValue.type
                        };
                        sco.changeSave = AccountChangeType.change(changeTypeParams);
                        sco.changeSave.$promise.then(function () {
                            el.find("button").addClass(initialValue.clazz);
                            sco.buttonText = initialValue.afterChangeBtText;
                            stateConfirm = 1;
                        }, function () {
                            alert("에러발생");
                        });
                    } else {
                        changeTypeParams = {
                            userId: userId,
                            requestType: initialValue.type,
                            remove: true
                        };
                        sco.changeSave = AccountChangeType.change(changeTypeParams);
                        sco.changeSave.$promise.then(function () {
                            el.find("button").removeClass(initialValue.clazz);
                            sco.buttonText = initialValue.beforeChangeBtText;
                            stateConfirm = -1;
                        }, function () {
                            alert("에러발생");
                        });
                    }
                }
            },
            replace: false
        }
    }]);
