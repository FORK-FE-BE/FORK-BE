package com.fork.app.service;


import com.fork.app.domain.entity.Address;
import com.fork.app.domain.entity.User;
import com.fork.app.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    public void save(Address address) {
        addressRepository.save(address);
    }

    // 📌 Read - 단건 조회
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소를 찾을 수 없습니다. id=" + id));
    }

    // 📌 Read - 유저별 전체 주소 목록
    public List<Address> findAllByUser(User user) {
        return addressRepository.findAllByUser(user);
    }

    // 📌 Update
    public Address updateAddress(Long id, Address updated) {
        Address address = getAddress(id, updated);
        return addressRepository.save(address); // 업데이트 후 저장
    }

    // 📌 Delete
    public void deleteById(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 주소가 존재하지 않습니다. id=" + id);
        }
        addressRepository.deleteById(id);
    }
    public void setDefaultAddress(Long addressId, User user) {
        // 1. 기존 기본 주소가 있다면 false로
        List<Address> userAddresses = addressRepository.findAllByUser(user);
        for (Address a : userAddresses) {
            if (a.getIsDefault()==1) {
                a.setIsDefault(0);
            }
        }
        // 2. 새로운 기본 주소로 지정
        Address target = findById(addressId);

        if (!target.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 주소는 해당 유저의 주소가 아닙니다.");
        }
        target.setIsDefault(1);
    }


    private Address getAddress(Long id, Address updated) {
        Address address = findById(id); // 기존 주소 조회
        address.setProvince(updated.getProvince());
        address.setCity(updated.getCity());
        address.setRoadName(updated.getRoadName());
        address.setBuildingNumber(updated.getBuildingNumber());
        address.setDetail(updated.getDetail());
        address.setPostalCode(updated.getPostalCode());
        address.setIsDefault(updated.getIsDefault());
        return address;
    }
}
