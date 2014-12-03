    function getFileContent() {

        var text = $("#cssFilesList option:selected").attr("title");
        $.ajax({
                type: 'GET',
                url: "/openmrs/module/custombranding/CssContent.form?path=" + text,
                dataType: 'text',
                async: true,
                success: function(response) {
                      $('#contentBox').text(response);
                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
        });
    }

    function toogleRecursiveSearchingAndList(elementId) {

        $.ajax({
                type: 'GET',
                url: "/openmrs/module/custombranding/SearchCssFiles.form",
                dataType: 'text',
                async: true,
                success: function(response) {
                     document.getElementById(elementId).options.length = 0;

                        jQuery.each( $.parseJSON(response), function(i, val) {
                            var x = document.getElementById(elementId);
                            var option = document.createElement("option");
                            option.text = val;
                            option.value = val;
                            option.title = i;
                            x.add(option);

                        });
                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
        });
    }

    function dbRequest(action) {

        if(action === "replaceCssFile" ){
            if( $("#cssFilesList option:selected").text() !== '' && $("#uploadCssFile").val() !== "" ) {
                
                var data = {
                    'action': action,
                    'content':   $("#uploadCssFile").val()
                }
                var func = location.reload(true);
                    ajaxRequest('POST', "/openmrs/module/custombranding/dbRequest.form", 'text', true, data, func, func);
                } else {
                    $("#errors").text("You need to choose css file to replace with pointed by you");
                }

        } else( $("#cssFilesList option:selected").text() !== '') {
            $.ajax({
                    type: 'POST',
                    url: "/openmrs/module/custombranding/dbRequest.form",
                    dataType: 'text',
                    async: true,
                    data: {
                            'action': action,
                            'content':   document.getElementById('contentBox').value
                            },
                    success: function(response) {
                          location.reload(true);
                    },
                    error: function() {
                        location.reload(true);
                    }
            });
        }
    }

    function ajaxRequest(_type, _url, _dataType, _async, _data, _succes, _error) {
         $.ajax({
            type: _type,
            url: _url,
            dataType: _dataType,
            async: _async,
            data: _data,
            success: f_succes,
            error: _error)
        });
    }

    function readSingleFile(e) {
          var file = e.target.files[0];
          if (!file) {
            return;
          }
          var reader = new FileReader();
          reader.onload = function(e) {
            var contents = e.target.result;
            displayContents(contents);
          };
          reader.readAsText(file);
    }

