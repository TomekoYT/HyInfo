plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.1-fabric"

stonecutter parameters {
    constants {
        match(node.metadata.project.substringAfterLast('-'), "fabric", "forge", "ornithe")
    }
}