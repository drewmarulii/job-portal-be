package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.Mcu;

@Repository
public class McuDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Mcu> getByApplicant(String id) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tm.file_id, tm.ver FROM t_mcu tm WHERE tm.applicant_id = :id");

		final List<?> mcuObjs = em().createNativeQuery(sql.toString()).setParameter("id", id).getResultList();
		final List<Mcu> mcus = new ArrayList<>();
		if (mcuObjs.size() > 0) {
			for (Object mcuObj : mcuObjs) {
				final Object[] mcuArr = (Object[]) mcuObj;
				final Mcu mcu = new Mcu();

				final File file = new File();
				file.setId(mcuArr[0].toString());
				mcu.setFile(file);
				mcu.setVersion(Integer.valueOf(mcuArr[1].toString()));
				mcus.add(mcu);
			}
		}
		
		return mcus;

	}
}
