package com.intergalactic.addressbook;

import com.google.inject.AbstractModule;
import com.intergalactic.addressbook.services.ContactZoomService;
import com.intergalactic.addressbook.views.ContactZoomView;

public class InjectionContainer extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(ContactZoomService.class).to(ContactZoomView.class);
	}

}
