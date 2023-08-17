package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.Image;

public interface IImageRepository extends JpaRepository<Image, Integer> {

}