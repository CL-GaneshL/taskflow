'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'employeeProjectsCtrl',
        [
            "$scope",
            "$timeout",
            function ($scope, $timeout) {

                // ==================================================
                // - initialize the projects 
                // ==================================================
                $scope.projects = [
                    {
                        label: 'Animal',
                        children: [{
                                label: 'Dog',
                                data: ''
                            }, {
                                label: 'Cat',
                                data: ''
                            }, {
                                label: 'Hippopotamus',
                                data: ''
                            }, {
                                label: 'Chicken',
                                children: ['White Leghorn', 'Rhode Island Red', 'Jersey Giant']
                            }]
                    },
                    {
                        label: 'Vegetable',
                        data: '',
                        children: [
                            {
                                label: 'Oranges'
                            }, {
                                label: 'Apples',
                                children: [
                                    {
                                        label: 'Granny Smith'
                                    }, {
                                        label: 'Red Delicous'
                                    }, {
                                        label: 'Fuji'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        label: 'Mineral',
                        children: [
                            {
                                label: 'Rock',
                                children: ['Igneous', 'Sedimentary', 'Metamorphic']
                            }, {
                                label: 'Metal',
                                children: ['Aluminum', 'Steel', 'Copper']
                            },
                            {
                                label: 'Plastic',
                                children: [
                                    {
                                        label: 'Thermoplastic',
                                        children: ['polyethylene', 'polypropylene', 'polystyrene', ' polyvinyl chloride']
                                    }, {
                                        label: 'Thermosetting Polymer',
                                        children: ['polyester', 'polyurethane', 'vulcanized rubber', 'bakelite', 'urea-formaldehyde']
                                    }
                                ]
                            }
                        ]
                    }
                ];

            }
        ]
        );