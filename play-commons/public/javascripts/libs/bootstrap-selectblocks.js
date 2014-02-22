/*!
 * bootstrap-selectblocks v1.0-SNAPSHOT
 * https://github.com/cokeSchlumpf/bootstrap-selectblocks
 *
 * Copyright 2013 Michael Wellner.
 * Licensed under the MIT License
 */

!function($) {

    'use strict';

    $.expr[':'].icontains = function(obj, index, meta) {
        return $(obj).text().toUpperCase().indexOf(meta[3].toUpperCase()) >= 0;
    };

    var Selectblocks = function(element, options, e) {
        if (e) {
            e.stopPropagation();
            e.preventDefault();
        }
        this.$element = $(element);
        this.$newElement = null;
        this.$links = null;

        //Merge defaults, options and data-attributes to make our options
        this.options = $.extend({}, $.fn.selectblocks.defaults, this.$element.data(), typeof options == 'object' && options);

        //If we have no title yet, check the attribute 'title' (this is missed by jq as its not a data-attribute)
        if (this.options.title === null) {
            this.options.title = this.$element.attr('title');
        }

        //Expose public methods
        // TODO
        this.val = Selectblocks.prototype.val;
        this.render = Selectblocks.prototype.render;
        this.refresh = Selectblocks.prototype.refresh;
        this.setStyle = Selectblocks.prototype.setStyle;
        this.selectAll = Selectblocks.prototype.selectAll;
        this.deselectAll = Selectblocks.prototype.deselectAll;
        this.init();
    };

    Selectblocks.prototype = {

        constructor: Selectblocks,

        init: function() {
            var that 	= this,
                id 		= this.$element.attr('id');

            this.$element.hide();
            this.multiple = this.$element.prop('multiple');
            this.autofocus = this.$element.prop('autofocus');
            this.$newElement = this.createView();
            this.$element.after(this.$newElement);
            
            this.clickListener();
            this.render();
            this.setStyle();
            this.$newElement.data('this', this);
        },

        createContainer: function() {
            //If we are multiple, then add the show-tick class by default
            var multiple = this.multiple ? ' show-tick' : '';
            var autofocus = this.autofocus ? ' autofocus' : '';
            
            var container = '<div class="selectblock-container">'
            	+ '</div>';

            return $(container);
        },

        createView: function() {
            var $container = this.createContainer();
            var $links = this.createLinks();
            $container.append($links);
            return $container;
        },

        reloadLi: function() {
            //Remove all children.
            this.destroyLi();
            //Re build
            var $links = this.createLinks();
            this.find('.selectblock-container').append( $links );
        },

        destroyLi: function() {
            this.find(".selectblock-container").html("");
        },

        createLinks: function() {
            var that 	= this,
                _liA 	= [],
                _liHtml = '';

            this.$element.find('option').each(function() {
                var $this = $(this);

                //Get the class and text for the option
                var optionClass = $this.attr('class') || '';
                var inline = $this.attr('style') || '';
                var text =  $this.data('content') ? $this.data('content') : $this.html();
                var subtext = $this.data('subtext') !== undefined ? '<small class="muted text-muted">' + $this.data('subtext') + '</small>' : '';
                var icon = $this.data('icon') !== undefined ? '<i class="' + that.options.iconBase + ' ' + $this.data('icon') + '"></i> ' : '';
                if (icon !== '' && ($this.is(':disabled') || $this.parent().is(':disabled'))) {
                    icon = '<span>'+icon+'</span>';
                }

                if (!$this.data('content')) {
                    //Prepend any icon and append any subtext to the main text.
                    text = icon + '<span class="text">' + text + subtext + '</span>';
                }

                if (that.options.hideDisabled && ($this.is(':disabled') || $this.parent().is(':disabled'))) {
                    _liA.push('<a style="min-height: 0; padding: 0"></a>');
                } else if ($this.parent().is('optgroup')) {
                    if ($this.index() === 0) {
                    	//Get the opt group label
                        var label = $this.parent().attr('label');
                        var labelSubtext = $this.parent().data('subtext') !== undefined ? '<small class="muted text-muted">'+$this.parent().data('subtext')+'</small>' : '';
                        var labelIcon = $this.parent().data('icon') ? '<i class="'+$this.parent().data('icon')+'"></i> ' : '';
                        label = labelIcon + '<span class="text">' + label + labelSubtext + '</span>';
                        
                        var liAContent = "";
                        
                        if ($this[0].index !== 0) {
                        	liAContent = "</div></div>";
                        }
                        
                        _liA.push(liAContent +
                    		'<div class="col-md-4">' + 
                        	'<h4 class="sb-select-group">' + label + '</h4>' +
                        	'<div class="list-group sb-list-group">' +
                            that.createA(text, 'opt ' + optionClass, inline));
                    } else {
                         _liA.push(that.createA(text, 'opt ' + optionClass, inline ));
                    }
                } else if ($(this).data('hidden') === true) {
                    _liA.push('');
                } else {
                    _liA.push(that.createA(text, optionClass, inline ));
                }
            });

            _liHtml = '<div class="row">';
            $.each(_liA, function(i, item) {
                _liHtml += item;
            });
            _liHtml += '</div>';

            //If we are not multiple, and we dont have a selected item, and we dont have a title, select the first element so something is set in the button
            if (!this.multiple && this.$element.find('option:selected').length===0 && !this.options.title) {
                this.$element.find('option').eq(0).prop('selected', true).attr('selected', 'selected');
            }
            
            return $(_liHtml);
        },

        createA: function(text, classes, inline) {
            return '<a tabindex="0" class="list-group-item sb-list-group-item '+classes+'" style="'+inline+'">' +
                 text +
                 '<span class="' + this.options.iconBase + ' ' + this.options.tickIcon + ' pull-right icon-check"></span>' +
                 '</a>';
        },

        render: function(updateLi) {
            var that = this;

            //Update the LI to match the SELECT
            if (updateLi !== false) {
                this.$element.find('option').each(function(index) {
                   that.setDisabled(index, $(this).is(':disabled') || $(this).parent().is(':disabled') );
                   that.setSelected(index, $(this).is(':selected') );
                });
            }

            this.tabIndex();

            var selectedItems = this.$element.find('option:selected').map(function() {
                var $this = $(this);
                var icon = $this.data('icon') && that.options.showIcon ? '<i class="' + that.options.iconBase + ' ' + $this.data('icon') + '"></i> ' : '';
                var subtext;
                if (that.options.showSubtext && $this.attr('data-subtext') && !that.multiple) {
                    subtext = ' <small class="muted text-muted">'+$this.data('subtext') +'</small>';
                } else {
                    subtext = '';
                }
                if ($this.data('content') && that.options.showContent) {
                    return $this.data('content');
                } else if ($this.attr('title') !== undefined) {
                    return $this.attr('title');
                } else {
                    return icon + $this.html() + subtext;
                }
            }).toArray();
        },

        setStyle: function(style, status) {
            if (this.$element.attr('class')) {
                this.$newElement.addClass(this.$element.attr('class').replace(/selectblocks|mobile-device/gi, ''));
            }
        },

        mobile: function() {
            this.$element.addClass('mobile-device').appendTo(this.$newElement);
        },

        refresh: function() {
            this.$links = null;
            this.reloadLi();
            this.render();
            this.setStyle();
        },
        
        update: function() {
            this.reloadLi();
            this.setStyle();
        },
        
        updateLinks: function() {
        	if (this.$links == null) this.$links = this.$newElement.find('a');
        },

        setSelected: function(index, selected) {
            this.updateLinks();
            $(this.$links[index]).toggleClass('selected', selected);
        },

        setDisabled: function(index, disabled) {
            this.updateLinks();
            if (disabled) {
                $(this.$links[index]).addClass('disabled').attr('href', '#').attr('tabindex', -1);
            } else {
                $(this.$links[index]).removeClass('disabled').removeAttr('href').attr('tabindex', 0);
            }
        },

        isDisabled: function() {
            return this.$element.is(':disabled');
        },

        tabIndex: function() {
            if (this.$element.is('[tabindex]')) {
                this.$element.data('tabindex', this.$element.attr('tabindex'));
            }
        },

        clickListener: function() {
            var that = this;
            
            this.$newElement.on("click", "a", function(e) {
            	var 	clickedIndex 	= -1,
            			prevValue 		= that.$element.val(),
            			prevIndex 		= that.$element.prop("selectedIndex");
            	
            	var thatA = this;
            	
            	that.$newElement.find("a").each(function(index, a) {
            		if (a === thatA) clickedIndex = index;
            	});
            	
            	if (that.multiple) {
            		e.stopPropagation();
            	}
            	
            	e.preventDefault();
            	
            	if (!that.isDisabled() && !$(this).hasClass("disabled")) {
            		var $options 	= that.$element.find("option"),
            			$option		= $options.eq(clickedIndex),
            			state 		= $option.prop("selected");
            		
            		if (!that.multiple) {
            			$options.prop("selected", false);
            			$option.prop("selected", true);
            			that.find(".selected").removeClass("selected");
            			that.setSelected(clickedIndex, true);
            		} else {
            			$option.prop("selected", !state);
            			that.setSelected(clickedIndex, !state);
            		}
            		
            		if ((prevValue != that.$element.val() && that.multiple) || (prevIndex != that.$element.prop("selectedIndex") && !that.multiple)) {
            			that.$element.change();
            		}
            	}
            });

            this.$element.change(function() {
                that.render(false);
            });
        },

        val: function(value) {

            if (value !== undefined) {
                this.$element.val( value );

                this.$element.change();
                return this.$element;
            } else {
                return this.$element.val();
            }
        },

        selectAll: function() {
            this.$element.find('option').prop('selected', true).attr('selected', 'selected');
            this.render();
        },

        deselectAll: function() {
            this.$element.find('option').prop('selected', false).removeAttr('selected');
            this.render();
        },

        hide: function() {
            this.$newElement.hide();
        },

        show: function() {
            this.$newElement.show();
        },

        destroy: function() {
            this.$newElement.remove();
            this.$element.remove();
        }
    };

    $.fn.selectblocks = function(option, event) {
       //get the args of the outer function..
       var args = arguments;
       var value;
       var chain = this.each(function() {
            if ($(this).is('select')) {
                var $this = $(this),
                    data = $this.data('selectblocks'),
                    options = typeof option == 'object' && option;

                if (!data) {
                    $this.data('selectblocks', (data = new Selectblocks(this, options, event)));
                } else if (options) {
                    for(var i in options) {
                       data.options[i] = options[i];
                    }
                }

                if (typeof option == 'string') {
                    //Copy the value of option, as once we shift the arguments
                    //it also shifts the value of option.
                    var property = option;
                    if (data[property] instanceof Function) {
                        [].shift.apply(args);
                        value = data[property].apply(data, args);
                    } else {
                        value = data.options[property];
                    }
                }
            }
        });

        if (value !== undefined) {
            return value;
        } else {
            return chain;
        }
    };

    $.fn.selectblocks.defaults = {
        style: '',
        hideDisabled: false,
        showSubtext: false,
        showIcon: true,
        showContent: true,
        multipleSeparator: ', ',
        iconBase: 'glyphicon',
        tickIcon: 'glyphicon-ok'
    };

}(window.jQuery);