package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.ApprovalAtasanRepository;
import com.hrs.checklist_resign.repository.PengajuanResignRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PengajuanResignService {

    @Autowired
    private PengajuanResignRepository pengajuanResignRepository;

    @Autowired
    private ApprovalAtasanRepository approvalAtasanRepository;

    public List<PengajuanResign> getAllResignations() {
        return pengajuanResignRepository.findAll();
    }

    public Optional<PengajuanResign> getResignationById(Long id) {
        return pengajuanResignRepository.findById(id);
    }

    public Optional<PengajuanResign> getResignationByUserDetail(UserDetail userDetail) {
        return pengajuanResignRepository.findByUserDetailResign(userDetail);
    }

    public Optional<PengajuanResign> getResignationByNipUser(String nipUser) {
        return pengajuanResignRepository.findByNipUser(nipUser);
    }

    public PengajuanResign saveResignation(PengajuanResign pengajuanResign) {
        return pengajuanResignRepository.save(pengajuanResign);
    }

    @Transactional
    public void deletePengajuanResign(String nipUser) {
        // Find the PengajuanResign entity by nipUser to get the nipKaryawanResign
        PengajuanResign pengajuanResign = pengajuanResignRepository.findByNipUser(nipUser)
                .orElseThrow(() -> new EntityNotFoundException("PengajuanResign not found with nipUser " + nipUser));

        String nipKaryawanResign = pengajuanResign.getNipUser();

        pengajuanResignRepository.deleteFinalApprovalByNipKaryawanResign(nipKaryawanResign);


        pengajuanResignRepository.deleteApprovalSecurityAdministratorByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalHRTalentByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalHRServicesAdminByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalHRPayrollByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalHRLearningByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalHRIRByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalGeneralServicesByNipKaryawanResign(nipKaryawanResign);
        pengajuanResignRepository.deleteApprovalTreasuryByNipKaryawanResign(nipKaryawanResign);



        // Delete child entities first


        pengajuanResignRepository.deleteApprovalAtasanByNipKaryawanResign(nipKaryawanResign);

        // Delete the parent entity
        pengajuanResignRepository.deletePengajuanResignByNipUser(nipUser);
    }




    public Page<PengajuanResign> findAllWithFiltersAndPagination(
                String nipKaryawan,
                String namaKaryawan,
                Integer filter,
                int page,
                int size,
                String sortBy,
                String sortDirection) {
            Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            return pengajuanResignRepository.findWithFilters(
                    nipKaryawan,
                    namaKaryawan,
                    filter,
                    pageable
            );
        }



}
