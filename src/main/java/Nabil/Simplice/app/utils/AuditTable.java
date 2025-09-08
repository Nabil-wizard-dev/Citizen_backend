package Nabil.Simplice.app.utils;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@CreatedDate @Column(updatable = false)
	private LocalDate createDate;
	@LastModifiedDate 
	private LocalDate updateDate;
	@CreatedBy @Column(updatable = false)
	private String createBy;
	@LastModifiedBy
	private String modifiedBy;
	
	public AuditTable(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super();
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createBy = createBy;
		this.modifiedBy = modifiedBy;
	}

	public AuditTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
