package com.EarthSandwich.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EarthSandwich.dto.CitiesResponseDTO;
import com.EarthSandwich.entity.Geo;

@Repository
public interface GeoDAO extends JpaRepository<Geo, String> {

	@Query(value = "SELECT new com.EarthSandwich.dto.CitiesResponseDTO(g.country, g.city, g.latitude, g.longitude, GCDistDeg(:latitude, :longitude, g.latitude/100, g.longitude/100) * 111.325 AS distance )   FROM Geo AS g ORDER BY distance ASC ")
	public Page<CitiesResponseDTO> findNearestCities(@Param("latitude") float latitude,
			@Param("longitude") float longitude, Pageable page);
}
