
<div class="container-fluid container-fullw bg-white no-border" >    

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Delete Non-Working Day</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new non working day form                         -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" role="form">

            <fieldset>

                <!-- ================================================== -->
                <!-- - title                                            -->
                <!-- ================================================== -->

                <legend>
                    Non-Working Day :
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

                <!-- ================================================== -->
                <!-- - non working date type                            -->
                <!-- ================================================== -->
                <div class="form-group padding-top-5 padding-left-10">

                    <div class="row col-md-12" ng-init="event.type">

                        <div class="radio clip-radio radio-warning col-md-5 text-left padding-left-15">
                            <input type="radio"
                                   ng-model="event.type"
                                   id="NON-WORKING"
                                   value="NON-WORKING" 
                                   ng-checked="event.type === 'NON-WORKING'"
                                   ng-readonly="true"
                                   disabled="" />
                            <label for="NON-WORKING">
                                Non-Working Day
                            </label>
                        </div>

                        <div class="radio clip-radio radio-success col-md-5 text-left padding-left-15">
                            <input type="radio"
                                   ng-model="event.type"
                                   id="WEEKEND"
                                   value="WEEKEND"
                                   ng-checked="event.type === 'WEEKEND'"
                                   ng-readonly="true"
                                   disabled="" />
                            <label for="WEEKEND">
                                Weekend Day
                            </label>
                        </div>

                    </div>

                </div>

            </fieldset>

            <!-- ================================================== -->
            <!-- - start date                                       -->
            <!-- ================================================== -->         
            <fieldset>

                <legend>
                    Date :
                </legend>

                <div class="form-group padding-left-10">

                    <div class="row col-md-12">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="event.date" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="event.min_date"
                                   max-date="event.max_date" 
                                   close-text="Close" 
                                   ng-readonly="true" />
                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

            </fieldset>


            <!-- ================================================== -->
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