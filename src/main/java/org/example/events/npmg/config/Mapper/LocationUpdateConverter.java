package org.example.events.npmg.config.Mapper;//package org.example.ticketmaster.config.Mapper;
//
//import org.example.ticketmaster.payload.Location;
//import org.modelmapper.Converter;
//import org.modelmapper.spi.MappingContext;
//
//public class LocationUpdateConverter implements Converter<Location, Location> {
//
//	@Override
//	public Location convert(MappingContext<Location, Location> context) {
//		Location source = context.getSource();
//		Location destination = context.getDestination();
//
//		if (source.getAddress() != null) {
//			destination.setAddress(source.getAddress());
//		}
//		if (source.getCity() != null) {
//			destination.setCity(source.getCity());
//		}
//		if (source.getZipCode() != null) {
//			destination.setZipCode(source.getZipCode());
//		}
//
//		return destination;
//	}
//}