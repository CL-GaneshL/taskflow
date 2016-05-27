
'use strict';

angular.module('calendar', ['mwl.calendar', 'ui.bootstrap', 'ngTouch', 'ngAnimate'])

        .controller('settingsHourlyCostCtrl',
                [
                    '$log',
                    "$scope",
                    "$uibModal",
                    "settingsSrvc",
                    "modalSrvc",
                    function ($log, $scope, $uibModal, settingsSrvc, modalSrvc) {

                        var CONTROLLER_NAME = 'settingsHourlyCostCtrl';

                        // ==================================================
                        // initialize the controller
                        // ==================================================

                        var TWO_DECIMALS = 2;

                        $scope.hourlyCost = '0.00';

                        var settingsPromise = settingsSrvc.getHourlyCost();
                        settingsPromise.then(
                                function (response) {

                                    var hourlyCost = response.hourlyCost.cost;

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : hourlyCost = " + JSON.stringify(hourlyCost));
                                    // --------------------------------------------------------

                                    setHourlyCost(hourlyCost);
                                },
                                function (response) {

                                    // ==================================================
                                    // - failed to change password 
                                    // ==================================================

                                    var status = response.status;
                                    var message = response.statusText;
                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                }
                        );

                        // ==================================================
                        // change password modal
                        // ==================================================
                        $scope.updateHourlyCost = function () {

                            var newHourlyCost = $scope.hourlyCost;

                            // 2 decimals value
                            var num = Number(newHourlyCost);
                            newHourlyCost = (num).toFixed(TWO_DECIMALS);

                            $uibModal.open(
                                    {
                                        templateUrl: 'taskflow/fragments/modal_hourly_cost_update',
                                        placement: 'center',
                                        size: 'sm',
                                        controller: function ($scope, $uibModalInstance) {
                                            $scope.newHourlyCost = newHourlyCost;
                                            $scope.$modalInstance = $uibModalInstance;

                                            $scope.cancel = function () {
                                                $uibModalInstance.dismiss('cancel');
                                            };

                                            $scope.updateHourlyCost = function () {

                                                var valid = true;
                                                var message = null;

                                                // --------------------------------------------------------
                                                $log.debug(CONTROLLER_NAME + " : $scope.newHourlyCost = " + JSON.stringify($scope.newHourlyCost));
                                                // --------------------------------------------------------                                                

                                                // is a numeric ?
                                                if (!isNumeric($scope.newHourlyCost)) {
                                                    valid = false;
                                                    message = "Hourly Cost must be a number.";
                                                }

                                                if (valid === false) {
                                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                                } else {

                                                    setHourlyCost($scope.newHourlyCost);

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : $scope.newHourlyCost = " + $scope.newHourlyCost);
                                                    // --------------------------------------------------------

                                                    var settingsPromise = settingsSrvc.updateHourlyCost($scope.newHourlyCost);
                                                    settingsPromise.then(
                                                            function (response) {

                                                                var msg = 'Hourly Cost updated.';
                                                                modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, msg);

                                                            },
                                                            function (response) {

                                                                // ==================================================
                                                                // - failed to change password 
                                                                // ==================================================

                                                                var status = response.status;
                                                                var message = response.statusText;
                                                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                                            }
                                                    );
                                                }

                                                $uibModalInstance.dismiss('updateHourlyCost');
                                            };
                                        }
                                    }
                            );
                        };

                        // ==================================================
                        // set the hourly cost with 2 decimals
                        // expect newHourlyCost to be a string representing
                        // a valid decimal number or integer.
                        // ==================================================
                        function setHourlyCost(newHourlyCost) {

                            var num = Number(newHourlyCost);
                            $scope.hourlyCost = (num).toFixed(TWO_DECIMALS);
                        }

                        // ==================================================
                        // define a "is a numeric value" funnction 
                        // ==================================================
                        // The (input - 0) expression forces JavaScript to do type 
                        // coercion on your input value; it must first be interpreted 
                        // as a number for the subtraction operation. If that 
                        // conversion to a number fails, the expression will 
                        // result in NaN. This numeric result is then compared 
                        // to the original value you passed in. Since the left 
                        // hand side is now numeric, type coercion is again used. 
                        // Now that the input from both sides was coerced to the 
                        // same type from the same original value, you would think 
                        // they should always be the same (always true). However, 
                        // there's a special rule that says NaN is never equal to 
                        // NaN, and so a value that can't be converted to a number 
                        // (and only values that cannot be converted to numbers) 
                        // will result in false. The check on the length is for 
                        // a special case involving empty strings.
                        // ==================================================
                        function isNumeric(input)
                        {
                            return (input - 0) == input && ('' + input).trim().length > 0;
                        }

                    }
                ]);

