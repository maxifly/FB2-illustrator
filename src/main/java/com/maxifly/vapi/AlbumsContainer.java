package com.maxifly.vapi;

import com.maxifly.vapi.model.AlbumAddrAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maximus on 21.12.2016.
 *
 * Контейнер всех объектов доступа к альбомам
 * Позволяет получить альбомы одного овнера или конкретный альбом
 * Так же позволяет определить все альбомы одного овнера сохранены или нет -
 * это важно для создания итератора по альбомам овнера.
 *
 * Возможно, этот объект может прикрывать собой в будущем какой-то кеш
 */
public class AlbumsContainer {

private HashMap<Long, Boolean> ownersFull = new HashMap<>();
private HashMap<Long,Map<AlbumAddrAttributes, AlbumProjects>> ownersAlbums = new HashMap<>();

    /**
     * Сохранить альбом
     * @param aaa атрибуты адреса альбома
     * @param albumProjects - объект доступа к проектам альбома
     */

    public void putAlbumProjects(AlbumAddrAttributes aaa, AlbumProjects albumProjects) {
        Map<AlbumAddrAttributes, AlbumProjects> albums =
                ownersAlbums.computeIfAbsent(aaa.ownerId, k -> new HashMap<>());
        albums.put(aaa,albumProjects);
    }

    /**
     * Получить проекты определенного альбома
     * @param aaa - атрибуты адреса альбома
     * @return проекты альбома
     */
    public AlbumProjects getAlbumProjects(AlbumAddrAttributes aaa){
        Map<AlbumAddrAttributes, AlbumProjects> albums =
                ownersAlbums.get(aaa.ownerId);
        if (albums == null) {
            return null;
        } else {
            return albums.get(aaa);
        }
    }

    /**
     * Получить количество альбомов с проектами, сохраненных для определенного пользователя
     * @return Количество альбомов
     */
    public int ownerAlbumCount(Long ownerId) {
        Map albums = ownersAlbums.get(ownerId);
        if (albums == null) {
            return 0;
        } else {
            return albums.size();
        }
    }

    /**
     * Сохранить признак того, что в контейнер переданы данные обо всех альбомах овнера
     */
    public void setOwnerFull(Long ownerId, boolean isFull) {
        ownersFull.put(ownerId,isFull);
         }

    /**
     * Получить признак того, что в контейнере сохранены данные обо всех альбомах овнера
     * @return все или нет
     */
    public boolean getOwnerFull(long ownerId) {
        return ownersFull.getOrDefault(ownerId,false);
    }

    /**
     * Очистить контейнер
     */
    public void refresh() {
        ownersFull.clear();
        ownersAlbums.clear();
    }

    /**
     * Очистить данные об овнере
     * @param ownerId - овнер
     */
    public void refresh(Long ownerId) {
        ownersFull.remove(ownerId);
        ownersAlbums.remove(ownerId);
    }

}
