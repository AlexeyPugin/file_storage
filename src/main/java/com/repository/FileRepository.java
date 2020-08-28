package com.repository;

import com.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

    @Query("select f from File f where f.name=:name")
    Optional<File> findOneByName(@Param("name") String name);

    @Query("select f from File f where f.name=:name and f.sizeInKb>=:minSizeKb and f.sizeInKb<=:maxSizeKb")
    Optional<File> findOneByNameRange(@Param("name") String name, @Param("minSizeKb") Integer minSizeKb, @Param("maxSizeKb") Integer maxSizeKb);

    @Query("select f from File f where f.sizeInKb>=:minSizeKb and f.sizeInKb<=:maxSizeKb")
    List<File> findOneByRange(@Param("minSizeKb") Integer minSizeKb, @Param("maxSizeKb") Integer maxSizeKb);
}
