/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive(
    "autoHeight",
    [
        function () {
            function calculateHeight(ratio, customMaxWidth, xsNumber, smNumber, mdNumber, lgNumber,
                                     noMaxWidth, noMargin, mode, margin) {
                var currentWidth = window.innerWidth;
                var currentHeight = 0;
                var defaultOption = {
                    raito: 0.7,
                    maxWidth: 1200,
                    xsNumber: 1,
                    smNumber: 2,
                    mdNumber: 3,
                    lgNumber: 3,
                    margin: 0.001,
                    noMaxWidth: false,
                    noMargin: false
                };
                if (ratio) {
                    defaultOption.raito = ratio;
                }
                if (noMargin) {
                    defaultOption.noMargin = noMargin;
                }
                if (margin) {
                    defaultOption.margin = margin;
                }
                if (currentWidth <= 599) {/* layout-sm */
                    /* xs */
                    if (xsNumber) {
                        defaultOption.xsNumber = xsNumber;
                    }
                    if (defaultOption.noMargin === "true") {
                        currentWidth = currentWidth / defaultOption.xsNumber;
                    } else {
                        currentWidth = currentWidth / defaultOption.xsNumber - currentWidth
                            * defaultOption.margin;
                    }
                    currentHeight = currentWidth * defaultOption.raito;
                } else if (currentWidth >= 600 && currentWidth <= 959) {/* layout-md */
                    /* sm */
                    if (smNumber) {
                        defaultOption.smNumber = smNumber;
                    }
                    if (mode === "detail") {
                        currentWidth = currentWidth * 0.9;
                    }
                    if (defaultOption.noMargin === "true") {
                        currentWidth = currentWidth / defaultOption.smNumber;
                    } else {
                        currentWidth = currentWidth / defaultOption.smNumber - currentWidth
                            * defaultOption.margin;
                    }
                    currentHeight = currentWidth * defaultOption.raito;
                } else if (currentWidth >= 960 && currentWidth <= 1199) {/* layout-lg */
                    /* md */
                    if (mdNumber) {
                        defaultOption.mdNumber = mdNumber;
                    }
                    if (mode === "detail") {
                        currentWidth = currentWidth * 0.7;
                    }
                    if (defaultOption.noMargin === "true") {
                        currentWidth = currentWidth / defaultOption.mdNumber;
                    } else {
                        currentWidth = currentWidth / defaultOption.mdNumber - currentWidth
                            * defaultOption.margin;
                    }
                    currentHeight = currentWidth * defaultOption.raito;
                } else if (currentWidth >= 1200) {/* layout-lg */
                    /* lg */
                    if (customMaxWidth) {
                        defaultOption.maxWidth = customMaxWidth;
                    }
                    if (noMaxWidth) {
                        defaultOption.noMaxWidth = noMaxWidth;
                    }
                    if (defaultOption.noMaxWidth !== "true") {
                        currentWidth = defaultOption.maxWidth;
                    }
                    if (lgNumber) {
                        defaultOption.lgNumber = lgNumber;
                    }
                    if (mode === "detail") {
                        currentWidth = currentWidth * 0.7;
                    }

                    if (defaultOption.noMargin === "true") {
                        currentWidth = currentWidth / defaultOption.lgNumber;
                    } else {
                        currentWidth = currentWidth / defaultOption.lgNumber - currentWidth
                            * defaultOption.margin;
                    }
                    currentHeight = currentWidth * defaultOption.raito;
                }

                return currentHeight;
            }

            return {
                restrict: "A",
                scope: {
                    ratio: '@',
                    noMaxWidth: '@',
                    noMargin: '@',
                    customMaxWidth: '@',
                    mode: '@',
                    margin: '@',
                    xsNumber: '@',
                    smNumber: '@',
                    mdNumber: '@',
                    lgNumber: '@'
                },
                link: function (scope, element) {
                    element.css("height", calculateHeight(scope.ratio, scope.customMaxWidth,
                            scope.xsNumber, scope.smNumber, scope.mdNumber, scope.lgNumber, scope.noMaxWidth,
                            scope.noMargin, scope.mode, scope.margin)
                        + "px");
                    $(window).resize(
                        function () {
                            element.css("height", calculateHeight(scope.ratio, scope.customMaxWidth,
                                    scope.xsNumber, scope.smNumber, scope.mdNumber, scope.lgNumber,
                                    scope.noMaxWidth, scope.noMargin, scope.mode, scope.margin)
                                + "px");
                        });
                }
            }
        }]);
