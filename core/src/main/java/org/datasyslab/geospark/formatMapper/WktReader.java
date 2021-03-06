/*
 * FILE: WktReader
 * Copyright (c) 2015 - 2019 GeoSpark Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.datasyslab.geospark.formatMapper;

import com.vividsolutions.jts.geom.Geometry;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datasyslab.geospark.enums.FileDataSplitter;
import org.datasyslab.geospark.spatialRDD.SpatialRDD;

public class WktReader extends RddReader
{
    /**
     * Read a SpatialRDD from a file.
     * @param sc
     * @param inputPath
     * @param wktColumn The column which contains the wkt string. Start from 0.
     * @param allowInvalidGeometries whether allows topology-invalid geometries exist in the generated RDD
     * @param skipSyntacticallyInvalidGeometries whether allows GeoSpark to automatically skip syntax-invalid geometries, rather than throw errors
     * @return
     */
    public static SpatialRDD<Geometry> readToGeometryRDD(JavaSparkContext sc, String inputPath, int wktColumn, boolean allowInvalidGeometries, boolean skipSyntacticallyInvalidGeometries) {
        JavaRDD rawTextRDD = sc.textFile(inputPath);
        FormatMapper<Geometry> formatMapper = new FormatMapper<Geometry>(wktColumn, -1, FileDataSplitter.WKT, true, null);
        formatMapper.allowTopologicallyInvalidGeometries = allowInvalidGeometries;
        formatMapper.skipSyntacticallyInvalidGeometries = skipSyntacticallyInvalidGeometries;
        return createSpatialRDD(rawTextRDD, formatMapper);
    }

    /**
     * Read a SpatialRDD from a string type rdd.
     * @param rawTextRDD a string type RDD
     * @param wktColumn The column which contains the wkt string. Start from 0.
     * @param allowInvalidGeometries whether allows topology-invalid geometries exist in the generated RDD
     * @param skipSyntacticallyInvalidGeometries whether allows GeoSpark to automatically skip syntax-invalid geometries, rather than throw errors
     * @return
     */
    public static SpatialRDD<Geometry> readToGeometryRDD(JavaRDD rawTextRDD, int wktColumn, boolean allowInvalidGeometries, boolean skipSyntacticallyInvalidGeometries) {
        FormatMapper<Geometry> formatMapper = new FormatMapper<Geometry>(wktColumn, -1, FileDataSplitter.WKT, true, null);
        formatMapper.allowTopologicallyInvalidGeometries = allowInvalidGeometries;
        formatMapper.skipSyntacticallyInvalidGeometries = skipSyntacticallyInvalidGeometries;
        return createSpatialRDD(rawTextRDD, formatMapper);
    }
}
