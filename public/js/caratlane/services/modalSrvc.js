'use strict';

/**
 * 
 * 
 */

app.factory("modalSrvc", function ($uibModal) {

    // ==================================================
    // - error message modal
    // ==================================================
    var showErrorMessageModal = function (message) {
        $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_error_message',
            placement: 'center',
            size: 'sm',
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.errorMessage = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    };

    // ==================================================
    // - error message modal 2
    // ==================================================
    var showErrorMessageModal2 = function (module, message) {
        $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_error_message2',
            placement: 'center',
            size: 'sm',
            modal: true,
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.module = module;
                $scope.status = status;
                $scope.message = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    };

    // ==================================================
    // - error message modal 3
    // ==================================================
    var showErrorMessageModal3 = function (module, status, message) {
        $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_error_message3',
            placement: 'center',
            size: 'sm',
            modal: true,
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.module = module;
                $scope.status = status;
                $scope.message = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    };

    // ==================================================
    // - success message modal
    // ==================================================
    function showSuccessMessageModal(message) {
        var modalInstance = $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_success_message',
            placement: 'center',
            size: 'sm',
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.successMessage = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    }

    // ==================================================
    // - success message modal 2
    // ==================================================
    function showSuccessMessageModal2(module, message) {
        var modalInstance = $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_success_message2',
            placement: 'center',
            size: 'sm',
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.module = module;
                $scope.message = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    }

    // ==================================================
    // - success message modal 2
    // ==================================================
    function showInformationMessageModal2(module, message) {
        var modalInstance = $uibModal.open({
            templateUrl: 'taskflow/fragments/modal_information_message2',
            placement: 'center',
            size: 'sm',
            controller: function ($scope, $uibModalInstance) {
                $scope.$modalInstance = $uibModalInstance;
                $scope.module = module;
                $scope.message = message;
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }
        });
    }

    // ==================================================

    return {
        showErrorMessageModal: showErrorMessageModal,
        showErrorMessageModal2: showErrorMessageModal2,
        showErrorMessageModal3: showErrorMessageModal3,
        showSuccessMessageModal: showSuccessMessageModal,
        showSuccessMessageModal2: showSuccessMessageModal2,
        showInformationMessageModal2: showInformationMessageModal2
    };

    // ==================================================
});