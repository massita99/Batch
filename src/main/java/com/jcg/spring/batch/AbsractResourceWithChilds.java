package com.jcg.spring.batch;

import org.springframework.core.io.AbstractResource;

import java.util.List;

abstract public class AbsractResourceWithChilds extends AbstractResource {

    abstract public List<AbsractResourceWithChilds> getChildResources();

    abstract public boolean isDirectory();

    abstract public boolean createChildResource(String name);

}
