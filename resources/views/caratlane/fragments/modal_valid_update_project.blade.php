
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Validation</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new skill form                                   -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="projectForm" >

            <fieldset>

                <legend>
                    Update the following Project ?
                </legend>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-left-0 padding-right-5">
                        Reference :
                    </label>

                    <div class="col-md-8">

                        <input type="text"
                               class="col-md-12"
                               name="reference" 
                               id="reference"
                               ng-model="toUpdateProjectReference" 
                               placeholder="<% toUpdateProjectReference %>" 
                               required
                               />

                    </div>
                </div>
                
                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-left-0 padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-8">
                        
                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="toUpdateStartDate" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="event.min_date"
                                   max-date="event.max_date" 
                                   close-text="Close" 
                                   />
                            <i class="ti-calendar"></i>
                        </span>                        

                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-left-0 padding-right-5">
                        Nb Products :
                    </label>

                    <div class="col-md-8">

                        <input type="text" 
                               class="col-md-12"
                               name="nbProducts"
                               id="nbProducts"
                               ng-model="toUpdateNbProducts" 
                               placeholder="<% toUpdateNbProducts %>"
                               required
                               />

                    </div>

                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-left-0 padding-right-5">
                        Priority :
                    </label>

                    <div class="col-md-8">

                        <select class="form-control cs-select cs-skin-elastic" 
                                ng-model="toUpdateProjectPriority"
                                name="priority"
                                id="priority"  
                                ng-options="choice for choice in priorityChoices" >
                            <option value="" disabled=""><% toUpdateProjectPriority %></option>
                        </select>

                    </div>

                </div>

            </fieldset>
            <!-- ================================================== -->
        </form>
    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="updateProject()">
            Update
        </button>

    </div>

</div>