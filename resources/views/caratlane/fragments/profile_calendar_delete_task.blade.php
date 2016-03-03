
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Delete Task</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - delete a task event                              -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" role="form">

            <fieldset>

                <!-- ================================================== -->
                <!-- - title                                            -->
                <!-- ================================================== -->
                <legend>
                    Task :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.title" 
                               class="form-control underline text-large" 
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>

            <fieldset>

                <!-- ================================================== -->
                <!-- - duration                                         -->
                <!-- ================================================== -->
                <legend>
                    Duration :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.duration" 
                               class="form-control underline text-large" 
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>
            
            <fieldset>

                <!-- ================================================== -->
                <!-- - completion                                         -->
                <!-- ================================================== -->
                <legend>
                    Completion :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.completion" 
                               class="form-control underline text-large" 
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - start date                                       -->
            <!-- ================================================== -->         
            <fieldset>

                <legend>
                    Start date :
                </legend>

                <div class="form-group padding-left-10">

                    <div class="row col-md-12">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="event.start_date" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="event.min_date"
                                   max-date="event.max_date" 
                                   close-text="Close" 
                                   ng-readonly="true"
                                   disabled="" />

                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

            </fieldset>

            <fieldset>

                <!-- ================================================== -->
                <!-- - shift hour                                       -->
                <!-- ================================================== -->
                <legend>
                    Shift hour  :
                </legend>

                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12">

                        <input type="text" 
                               ng-model="event.shift_hour" 
                               class="form-control underline text-large" 
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>

        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-md-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="cancel()">
                Cancel
            </button>

            <button class="btn btn-primary btn-o" ng-click="delete()">
                Delete
            </button>

        </div>

    </div>

</div>