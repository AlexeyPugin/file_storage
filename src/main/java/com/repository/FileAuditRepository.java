package com.repository;

import com.domain.FileAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAuditRepository extends JpaRepository<FileAudit, String> {

}
