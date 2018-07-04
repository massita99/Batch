package com.jcg.spring.batch;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.integration.smb.session.SmbSession;
import org.springframework.integration.smb.session.SmbSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Setter
public class SmbResource extends AbsractResourceWithChilds {

    private SmbSessionFactory smbSessionFactory;
    private String filePath;

    public SmbResource(String filePath, SmbSessionFactory factory) {
        // TODO: 6/29/2018 check session change to afterpropertyset
        this.filePath = filePath;
        this.smbSessionFactory = factory;
    }

    @Override
    public String getDescription() {
        return toString();
    }

    private SmbFile getSmbFile() throws IOException {
        SmbSession session = smbSessionFactory.getSession();
        return session.createSmbFileObject(filePath);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return getSmbFile().getInputStream();
    }

    @Override
    public boolean exists() {
        try {
            return getSmbFile().exists();
        } catch (SmbException e) {
            // TODO: 6/29/2018 LOG 
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isReadable() {
        try {
            return getSmbFile().canRead();
        } catch (SmbException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getFilename() {
        try {
            return getSmbFile().getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "SmbResource{" +
                "filePath='" + filePath + '\'' +
                "} ";
    }

    public List<AbsractResourceWithChilds> getChildResources() {
        SmbFile thisResource = null;
        List<AbsractResourceWithChilds> result = new ArrayList<>();
        try {
            thisResource = getSmbFile();
            if (thisResource.isDirectory()) {
                for (String file : thisResource.list()) {
                    result.add(new SmbResource(file, smbSessionFactory));
                }
            } else {
                result = Collections.emptyList();
            }
        } catch (IOException e) {
            result = Collections.emptyList();
            e.printStackTrace();
            // TODO: 6/29/2018 LOG is not a dir or is not avaialble
        }
        return result;
    }

    @Override
    public boolean isDirectory() {
        try {
            return getSmbFile().isDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createChildResource(String name) {
        //TODO create resource
        return false;
    }

}
