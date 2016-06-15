
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
                    Close the following Project ?
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
                               placeholder="<% toCloseProjectReference %>" 
                               ng-readonly="true"
                               disabled="" />

                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-left-0 padding-right-5">
                        Priority :
                    </label>

                    <div class="col-md-8">

                        <input type="text" 
                               class="col-md-12"
                               name="priority"
                               id="priority"
                               placeholder="<% toCloseProjectPriority %>"
                               ng-readonly="true"
                               disabled="" />

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
                               placeholder="<% toCloseNbProducts %>"
                               ng-readonly="true"
                               disabled="" />

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

        <button class="btn btn-primary btn-o" ng-click="closeProject()">
            Close
        </button>

    </div>

</div>