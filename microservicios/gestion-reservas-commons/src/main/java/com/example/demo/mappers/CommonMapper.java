package com.example.demo.mappers;

public interface CommonMapper<RQ, RS, E> {
	RS entityToResponse(E entity);
	
	E requestToEntity(RQ request);
	
	E updateEntityFromRequest( RQ Request, E entity);

}
